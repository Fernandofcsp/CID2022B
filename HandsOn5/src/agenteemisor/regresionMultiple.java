/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agenteemisor;


import java.util.ArrayList;


public class regresionMultiple {
    private double b0 = 0, b1 = 0, b2 = 0;
    ArrayList<Dataset> dataset = new ArrayList<Dataset>();
            
    public void crearMatriz(){
        

        //Sacar tamaño de matriz
        int filas = dataset.size();
        int columnas = 3;

        //Matriz X    200*3
        double matrizX[][] = new double[filas][columnas]; 
        for(int i = 0; i<dataset.size(); i++){
            matrizX[i][0] = 1;
            for(int j = 1; j < matrizX[i].length; j++){
                matrizX[i][j++] = dataset.get(i).getRadio();
                matrizX[i][j++] = dataset.get(i).getNPaper();
            }
        }

        //Matriz Y      1*200
        double matrizY[][] = new double[filas][1];
        for (int i = 0; i < matrizY.length; i++) {
            for (int j = 0; j < matrizY[i].length; j++) {
                matrizY[i][j++] = dataset.get(i).getSales();
            }
        }


        //Matriz X Transpuesta    3*200
        filas = matrizX.length; //200
        columnas = matrizX[0].length; //4
        double matrizXT[][] = new double[columnas][filas]; //Se invierten filas por columnas
        for (int i = 0; i < matrizX.length; i++) {
            for (int j = 0; j < matrizX[1].length; j++) {
                matrizXT[j][i] = matrizX[i][j];
            }
        }

        //Producto de XT * X    3x3
        double[][] producto = new double[matrizXT.length][matrizX[0].length];
        for (int a = 0; a < matrizX[0].length; a++) {
            // Dentro recorremos las filas de la primera (A)
            for (int i = 0; i < matrizXT.length; i++) {
                int suma = 0;
                // Y cada columna de la primera (A)
                for (int j = 0; j < matrizXT[0].length; j++) {
                    // Multiplicamos y sumamos resultado
                    suma += matrizXT[i][j] * matrizX[j][a];
                }
                // Lo acomodamos dentro del producto
                producto[i][a] = suma;
            }
        }

        //Matriz inversa 3x3
        double matrizI[][] = producto;
        matrizI = matrizInversa(matrizI);

        //Producto de inversa por transpuesta   3x200
        double[][] productoIT = new double[matrizI.length][matrizXT[0].length];
        for (int i = 0; i < productoIT.length; i++)
            for (int j = 0; j < productoIT[0].length; j++)
                for (int k = 0; k < matrizXT.length; k++)
                    productoIT[i][j] = productoIT[i][j] + matrizI[i][k] * matrizXT[k][j];


        //Producto de transpuesta x matrizY
        double[][] productoTY = new double[matrizXT.length][matrizY[0].length];
        for (int i = 0; i < productoTY.length; i++)
            for (int j = 0; j < productoTY[0].length; j++)
                for (int k = 0; k < matrizY.length; k++)
                    productoTY[i][j] = productoTY[i][j] + matrizXT[i][k] * matrizY[k][j];

        // Producto de inversa por Y 
        double[][] betas = new double[matrizI.length][productoTY[0].length];
        for (int i = 0; i < betas.length; i++)
            for (int j = 0; j < betas[0].length; j++)
                for (int k = 0; k < productoTY.length; k++)
                    betas[i][j] = betas[i][j] + matrizI[i][k] * productoTY[k][j];
        
        this.b0 = betas[0][0];
        this.b1 = betas[1][0];
        this.b2 = betas[2][0];

    }
    public double[][] matrizInversa(double[][] matriz) {
        double det = 1 / determinante(matriz);
        double[][] nmatriz = matrizAdjunta(matriz);
        multiplicarMatriz(det, nmatriz);
        return nmatriz;
    }

    public void multiplicarMatriz(double n, double[][] matriz) {
        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz.length; j++)
                matriz[i][j] *= n;
    }

    public double[][] matrizAdjunta(double[][] matriz) {
        return matrizTranspuesta(matrizCofactores(matriz));
    }

    public double[][] matrizCofactores(double[][] matriz) {
        double[][] nm = new double[matriz.length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                double[][] det = new double[matriz.length - 1][matriz.length - 1];
                double detValor;
                for (int k = 0; k < matriz.length; k++) {
                    if (k != i) {
                        for (int l = 0; l < matriz.length; l++) {
                            if (l != j) {
                                int indice1 = k < i ? k : k - 1;
                                int indice2 = l < j ? l : l - 1;
                                det[indice1][indice2] = matriz[k][l];
                            }
                        }
                    }
                }
                detValor = determinante(det);
                nm[i][j] = detValor * (double) Math.pow(-1, i + j + 2);
            }
        }
        return nm;
    }

    public double[][] matrizTranspuesta(double[][] matriz) {
        double[][] nuevam = new double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++)
                nuevam[i][j] = matriz[j][i];
        }
        return nuevam;
    }

    public double determinante(double[][] matriz) {
        double det;
        if (matriz.length == 2) {
            det = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
            return det;
        }
        double suma = 0;
        for (int i = 0; i < matriz.length; i++) {
            double[][] nm = new double[matriz.length - 1][matriz.length - 1];
            for (int j = 0; j < matriz.length; j++) {
                if (j != i) {
                    for (int k = 1; k < matriz.length; k++) {
                        int indice = -1;
                        if (j < i)
                            indice = j;
                        else if (j > i)
                            indice = j - 1;
                        nm[indice][k - 1] = matriz[j][k];
                    }
                }
            }
            if (i % 2 == 0)
                suma += matriz[i][0] * determinante(nm);
            else
                suma -= matriz[i][0] * determinante(nm);
        }
        return suma;
    }
    public void predecir(double x1, double x2, ArrayList<Dataset> dataset ){
        
        this.dataset=dataset;
        crearMatriz();
        //Formula de MLR
        double predict = this.b0 + this.b1*x1 + this.b2*x2;	
        
        System.out.println("Beta0 = "+this.b0+"\n");
        System.out.println("Beta1 = "+this.b1+"\n");
        System.out.println("Beta2 = "+this.b2+"\n");
     
        System.out.println("\nCuando radio(X1)= "+x1+" y NewsPaper(X2)= "+x2);
        System.out.println("La prediccion para sales es: "+ predict);
    }
}
