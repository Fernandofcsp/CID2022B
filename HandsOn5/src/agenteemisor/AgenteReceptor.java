package agenteemisor;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;


public class AgenteReceptor extends Agent{
    arranqueGui myGui;
    ArrayList<Dataset> dataset = new ArrayList<Dataset>();

    protected void setup() {
        addBehaviour(new ReceptorComportamiento());
        myGui = new arranqueGui(this);
    }

    //CLASE COMPORTAMIENTO CON SIMPLE BEHAVIOUR
    private class ReceptorComportamiento extends SimpleBehaviour {
        private boolean fin = false;

        public void action() {
            ACLMessage mensaje = receive();
            if (mensaje != null) {
                
                System.out.println(getLocalName() + ": acaba de recibir un mensaje: ");
                asignaDatos(mensaje.getContent().toString());
                myGui.showGui();
                fin = true;
            }
        }

        public void asignaDatos(String data) {

            // Remover caracteres
            String charsToRemove = "\"[]";
            for (char c : charsToRemove.toCharArray()) {
                data = data.replace(String.valueOf(c), "");
            }

            String[] linea = data.split(",");

            double aux;
            int columna1;
            double tv;
            double radio;
            double nPaper;
            double sales;
           
            for (int i = 5; i < linea.length; i += 0) {
                aux = Double.parseDouble(linea[i++]);
                columna1 = (int)aux;
                tv = Double.parseDouble(linea[i++]);
                radio = Double.parseDouble(linea[i++]);
                nPaper = Double.parseDouble(linea[i++]);
                sales = Double.parseDouble(linea[i++]);
                Dataset start = new Dataset(columna1, tv, radio, sales, nPaper);
                dataset.add(start);
            }
        }

        public boolean done() {
            return fin;
        }
    }
    public void main(double x1, double x2){
    
        regresionMultiple ln = new regresionMultiple();
        ln.predecir(x1, x2, dataset);
    }
}
