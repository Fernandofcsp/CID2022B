package agenteemisor;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
 
public class AgenteReceptor extends Agent {
    
    arranqueGui myGui;
    ArrayList<Dataset> dataset = new ArrayList<Dataset>();
    public float b0 = 0, b1 = 0;
    
    protected void setup() {
        addBehaviour(new ReceptorComportamiento());
        myGui = new arranqueGui(this);
        
    }

   private class ReceptorComportamiento extends SimpleBehaviour {
       
        private boolean fin = false;
    
        public void action() {
            ACLMessage mensaje = receive();

            if (mensaje != null) {
                System.out.println(getLocalName() + ": acaba de recibir un mensaje: ");
                System.out.println(mensaje.getContent());
                asignaDatos(mensaje.getContent().toString());
                myGui.showGui();
                fin = true;
            }
        }
        public boolean done() {
            return fin;
        }
   }

        public void asignaDatos(String data){

            //Remover caracteres
            String charsToRemove = "\"[]";
            for (char c : charsToRemove.toCharArray()) {
                data = data.replace(String.valueOf(c), "");
            }

            String[] linea = data.split(","); 

            float rdSpend;
            float administration;
            float mSpend;
            float profit;
            String state;

            for(int i = 5; i<linea.length; i+=0){
                rdSpend = Float.parseFloat(linea[i++]);
                administration = Float.parseFloat(linea[i++]);
                mSpend = Float.parseFloat(linea[i++]);
                state = linea[i++];
                profit = Float.parseFloat(linea[i++]);
                Dataset start = new Dataset(rdSpend, administration, mSpend, profit, state);
                dataset.add(start);
            }
        }

        void regression(final float n) {
            
            float rdSpend = 0, rdSpend2 = 0, profit = 0, madProfit = 0;
            //Prediccion de Profit en funcion de R&DSpend
            for (int i = 0; i < dataset.size(); i++) {
                rdSpend += dataset.get(i).getrdSpend(); //Sumatoria r&dspend
                profit += dataset.get(i).getProfit(); //Sumatoria profit
                rdSpend2 += Math.pow(dataset.get(i).getrdSpend(), 2); //Sumatoria de raíz cuadrada de r&dspend
                madProfit += dataset.get(i).getrdSpend() * dataset.get(i).getProfit(); //Sumatoria de r&dspend * profit
            }

            //((nDatos * (sumatoria de r&dSpend * profit) - sumatoria r&dSpend * sumatoria de profit) /
            //(nDatos * sumayoria de la raíz cuadrada de r&dSpend - sumatoria de r&dSpend * sumatoria de r&dSpend))
            b1 = ((dataset.size() * madProfit - rdSpend * profit) / (dataset.size() * rdSpend2 - rdSpend * rdSpend));
            //((Sumatoria de profit - B1 * sumatoria de r&dSpend) / nDatos)
            b0 =((profit - b1 * rdSpend) / dataset.size()); 

            System.out.println("Beta 0 = " + b0 + " Beta 1 = " + b1);
            System.out.println("Predecir Profit en funcion de R&DSpend");
            System.out.println("Profit es igual a: " + (b0 + b1 * n) + " Si R&DSpend es igual a: " + n);
        }          
}

