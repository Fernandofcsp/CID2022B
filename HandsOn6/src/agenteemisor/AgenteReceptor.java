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
    double[] datasetX;
    double[] datasetY;

    protected void setup() {
        addBehaviour(new ReceptorComportamiento());
        myGui = new arranqueGui(this);
        System.out.println("Soy un agente de regresion lineal via gradiente descendiente");
    }
    private class ReceptorComportamiento extends SimpleBehaviour {
        
        private boolean fin = false;

        public void action() {
            ACLMessage mensaje = receive();

            if (mensaje != null) {
                System.out.println(getLocalName() + ": Datos recibidos: ");

                asignaDatos(mensaje.getContent().toString());
                //System.out.println(mensaje.getContent().toString());
                myGui.showGui();
                fin = true;
            }
        }
        public boolean done() {
            return fin;
        }
    }

    public void asignaDatos(String data) {
        // Remover caracteres
        String charsToRemove = "\"[]";
        for (char c : charsToRemove.toCharArray()) {
            data = data.replace(Character.toString(c), "");
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
            
        int filas = this.dataset.size();
        datasetX = new double[filas];
        datasetY = new double[filas];

        //Guardar datos en matriz
        for (int i = 0; i < datasetX.length; i++){
            datasetX[i] = dataset.get(i).getRadio(); //variable X
            datasetY[i] = dataset.get(i).getSales(); //variable Y(Sales)
        }
    }

     public void main(final double x) {
             gradienteSLR ln = new gradienteSLR();
             System.out.println(datasetY);
             ln.predecir(datasetY,datasetX,x);   
     }
}

  

