package agenteemisor;

import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.io.*;

public class AgenteEmisor extends Agent {

  protected void setup() {
        addBehaviour(new EmisorComportamiento());
    }

   private class EmisorComportamiento extends SimpleBehaviour {
        boolean fin = false;
        public ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
      
        public void action() {
            datos = readCSV();
            System.out.println(getLocalName() +": Enviando datos al receptor");
            AID id = new AID();
            id.setLocalName("receptor");

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(id);
            String cadena = Arrays.deepToString(datos.toArray());
            msg.setSender(getAID());
            msg.setContent(cadena);
            msg.setLanguage("English");
            send(msg);
            
            fin = true;
        //Envia el mensaje a los destinatarios
        }
 
        public boolean done()
        {
            return fin;
        }
        //Funcion para Leer el archivo csv
        public ArrayList<ArrayList<String>> readCSV(){
            try{
                BufferedReader reader = new BufferedReader(new FileReader("C:/50_Startups.csv"));
                String line = null;
                while((line = reader.readLine()) != null){
                    String [] parts = line.split(",");
                    ArrayList<String> datosTmp = new ArrayList<String>();
                    for(String dato : parts){
                        datosTmp.add(dato);
                    }
                    datos.add(datosTmp);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return datos;
        }
    }
}