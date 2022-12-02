/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenteemisor;

public class gradienteSLR {
    private double B0=0; //peso 0
    private double B1=0; //peso 0
    private double alfa=0.003; //Tasa de aprendizaje
    private double proximo=0.01; //Determina las iteracion en base a la funcion de perdida
    
    //Funcion de perdida
    public double LossFunction(double[] datasetY,double[] datasetX){
    	double n=datasetX.length; //Obtiene el tamaño de la variable y del dataset
        double s=0.0; //Valor inicial de la funcion de perdida
        //Calculo del valor
    	for(int i=0;i<n;i++){
           s+=(datasetY[i]-(B0+B1*datasetX[i]));
        }
        s=s/n;
        s=s*s;
        return s;
    }
    
    //Derivada Parcial de B0
    public double dPb0(double[] datasetY,double[] datasetX){
    	double n=datasetX.length;
        double dpb0=0.0;
        //Calculo de la derivada
    	for(int i=0;i<n;i++){
    		dpb0 = (datasetY[i]-(B0+(B1*datasetX[i])));
        }
        dpb0=(-2.0/n)*dpb0;
    	return dpb0;
    }
    
    //Derivada Parcial de B1
    public double dPb1(double[] datasetY,double[] datasetX){
    	double n=datasetX.length;
        double dpb1=0.0;
        //calculo dela derivada
    	for(int i=0;i<n;i++){
            dpb1 = ((datasetY[i]-(B0+(B1*datasetX[i])))*datasetX[i]);
        }
        dpb1=(-2.0/n)*dpb1;
    	return dpb1;
    }
   
    public void predecir(double[] datasetY, double[] datasetX, double x){
                //Mientras que la funcion de perdida sea mayor al proximo
		while(LossFunction(datasetY,datasetX)>proximo){
                        B0=B0-alfa*dPb0(datasetY,datasetX);
                        B1=B1-alfa*dPb1(datasetY,datasetX);
		}
		System.out.println("Cuando x = "+x+" La prediccion es = "+((B1 * x) + B0));
		System.out.println("B0 = " + B0  +  " B1 = "+ B1 +"  perdida:" + LossFunction(datasetY,datasetX));
	}
}
