/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements 
                                                          IArbolBusqueda<K, V> {
    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {
    }
    // constructor de un arbol apartir de listas de claves y valores usando 
     // el recorrido  preorden,inorden  y el postorden,inorden
    public ArbolBinarioBusqueda(List<K> listaDeClavesInOrden,List<K> 
                    listaDeClavesNoInOrden,List<V> listaDeValoresInOrden,List<V> 
                    listaDeValoresNoInOrden, boolean conPostOrden){
       
      // verificar si alguna de las listas esta vacia  
     if(listaDeClavesInOrden.isEmpty()||listaDeClavesNoInOrden.isEmpty() ||
        listaDeValoresInOrden.isEmpty()|| listaDeValoresNoInOrden.isEmpty()){
       throw new IllegalArgumentException("ERROR las listas no pueden estar "
                                          + "vacias");  
     }    
     // verificamos si alguna de las listas contiene algun valor nulo
     if(this.contieneClavesNulas(listaDeClavesInOrden)||
        this.contieneClavesNulas(listaDeClavesNoInOrden)||
        this.contieneValoresNulos(listaDeValoresInOrden)||
        this.contieneValoresNulos(listaDeValoresNoInOrden)){
      throw new IllegalArgumentException("ERROR las listas no pueden tener"
                                        +"valores nulos");   
     }
     // reconstruimos el arbol segun el metodo especificado 
     if(conPostOrden){
       this.raiz=this.reconstruirConPostOrden(listaDeClavesInOrden,
         listaDeClavesNoInOrden,listaDeValoresInOrden,listaDeValoresNoInOrden);  
     }else{
       this.raiz=this.reconstruirConPreOrden(listaDeClavesInOrden,
         listaDeClavesNoInOrden,listaDeValoresInOrden,listaDeValoresNoInOrden);  
      }
    }
    
  //metodo privado para verificar si una lista de claves contiene valores nulos
    private boolean contieneClavesNulas(List<K> listaDeClaves){
      for(K claveDeLaLista: listaDeClaves){
        if(claveDeLaLista==null){
           return true; 
        }   
      }  
      return false; 
    }
    
  //metodo privado para verificar si una lista de valores contiene valores nulos
     private boolean contieneValoresNulos(List<V> listaDeValores){
       for(V valorDeLaLista: listaDeValores){
        if(valorDeLaLista==null){
           return true;  
        }  
       }
      return false; 
    }
    
  //metodo protegido para reconstruir el árbol a partir de listas de claves 
  // y valores en postorden  
    protected NodoBinario<K, V> reconstruirConPostOrden(List<K> 
      listaDeClavesInOrden, List<K>listaDeClavesNoInOrden, 
      List<V> listaDeValoresInOrden, List<V> listaDeValoresNoInOrden) {
       
      // caso base : las listas estan vacias  
      if(listaDeClavesInOrden.isEmpty() && listaDeClavesNoInOrden.isEmpty() &&
        listaDeValoresInOrden.isEmpty() && listaDeValoresNoInOrden.isEmpty()){  
        return NodoBinario.nodoVacio();
      }
     // caso base : las listas tienen un solo elemento 
     if(listaDeClavesInOrden.size()==1 && listaDeClavesNoInOrden.size()==1 &&
        listaDeValoresInOrden.size()==1 && listaDeValoresNoInOrden.size()==1){
         
       K claveDeLaLista=listaDeClavesInOrden.get(0);
       V valorDeLaLista=listaDeValoresNoInOrden.get(0);
       NodoBinario<K,V> nuevoNodo=new NodoBinario(claveDeLaLista,
                                                                valorDeLaLista);
       return nuevoNodo;        
     }
     //obtenemos la clave y el valor del ultimo elemento de las listas noInOrden 
     int ultimaPosicionDeLaListaNoInOrden=listaDeClavesNoInOrden.size()-1; 
     K claveDeLaListaNoInOrden=listaDeClavesNoInOrden.get(
                                             ultimaPosicionDeLaListaNoInOrden);
     V valorDeLaListaNoInOrden=listaDeValoresNoInOrden.get(
                                             ultimaPosicionDeLaListaNoInOrden);
     
     // encontrar la posicion de la clave y valor de las listas InOrden  
     int posicionClaveInOrden=listaDeClavesInOrden.indexOf(
                                                       claveDeLaListaNoInOrden);
     int posicionValorInOrden=listaDeValoresInOrden.indexOf(
                                                       valorDeLaListaNoInOrden);
     
     // dividir las listas en sublistas
     List<K> subListaDeClavesInOrdenPorIzquierda=
                           listaDeClavesInOrden.subList(0,posicionClaveInOrden);
     List<K> subListaDeClavesInOrdenPorDerecha=listaDeClavesInOrden.
                    subList(posicionClaveInOrden+1,listaDeClavesInOrden.size());
     
     // realizar la misma división para las listas noInorden y la de valores
     
     List<K> subListaDeClavesNoInOrdenPorIzquierda=
                         listaDeClavesNoInOrden.subList(0,posicionClaveInOrden);
     List<K> SubListaDeClavesNoInOrdenPorDerecha=listaDeClavesNoInOrden.
                  subList(posicionClaveInOrden,listaDeClavesNoInOrden.size()-1);
     
     List<V> subListaDeValoresInOrdenPorIzquierda=
                          listaDeValoresInOrden.subList(0,posicionValorInOrden);  
     List<V> subListaDeValoresInOrdenPorDerecha=listaDeValoresInOrden.
                   subList(posicionValorInOrden+1,listaDeValoresInOrden.size());
     List<V> subListaDeValoresNoInOrdenPorIzquierda=listaDeValoresNoInOrden.
                                                subList(0,posicionValorInOrden);
     List<V> SubListaDeValoresNoInOrdenPorDerecha=listaDeValoresNoInOrden.
                 subList(posicionValorInOrden,listaDeValoresNoInOrden.size()-1);
     
    // llamadas recursivas para construir los nodos hijos izquierdo y derecho  
     NodoBinario<K,V> nuevoNodoPorIzquierda=this.reconstruirConPostOrden
   (subListaDeClavesInOrdenPorIzquierda,subListaDeClavesNoInOrdenPorIzquierda,
   subListaDeValoresInOrdenPorIzquierda,subListaDeValoresNoInOrdenPorIzquierda);
     
    NodoBinario<K,V> nuevoNodoPorDerecha=this.reconstruirConPostOrden
  (subListaDeClavesInOrdenPorDerecha, SubListaDeClavesNoInOrdenPorDerecha,
   subListaDeValoresInOrdenPorDerecha,SubListaDeValoresNoInOrdenPorDerecha);
    
    // construir y devolver el nodoActual
    NodoBinario<K,V> nuevoNodoActual=new NodoBinario(claveDeLaListaNoInOrden,
                                                 valorDeLaListaNoInOrden);
    nuevoNodoActual.setHijoIzquierdo(nuevoNodoPorIzquierda);
    nuevoNodoActual.setHijoDerecho(nuevoNodoPorDerecha);
    return nuevoNodoActual; 
   }
   // NOTA.- se puede hacer con 3 listas 
    
    //metodo protegido para reconstruir el árbol a partir de listas de claves 
    // y valores en preorden  
    protected NodoBinario<K,V>reconstruirConPreOrden(List<K> 
                     listaDeClavesInOrden,List<K>listaDeClavesNoInOrden,List<V> 
                     listaDeValoresInOrden,List<V> listaDeValoresNoInOrden){
        
         // caso base : las listas estan vacias
        if(listaDeClavesInOrden.isEmpty() &&listaDeClavesNoInOrden.isEmpty() &&
         listaDeValoresInOrden.isEmpty() &&listaDeValoresNoInOrden.isEmpty()){
          return NodoBinario.nodoVacio();
        }
         // caso base : las listas tienen un elemento
        if(listaDeClavesInOrden.size()==1&& listaDeClavesNoInOrden.size()==1 &&
         listaDeValoresInOrden.size()==1 && listaDeValoresNoInOrden.size()==1){
          K claveDeLaLista=listaDeClavesInOrden.get(0);
          V valorDeLaLista=listaDeValoresNoInOrden.get(0);
          NodoBinario<K,V> nuevoNodo= new NodoBinario
                                                (claveDeLaLista,valorDeLaLista);
          return nuevoNodo;
        }
      //obtener la clave y el valor del primer elemento de las listas NoInOrden
        final int PRIMERA_POSICION_DE_LA_LISTA_NOINORDEN = 0;
        K claveDeLaListaNoInOrden = 
             listaDeClavesNoInOrden.get(PRIMERA_POSICION_DE_LA_LISTA_NOINORDEN);
        V valorDeLaListaNoInOrden = 
            listaDeValoresNoInOrden.get(PRIMERA_POSICION_DE_LA_LISTA_NOINORDEN);
        int posicionClaveInOrden =
                         listaDeClavesInOrden.indexOf(claveDeLaListaNoInOrden);
        int posicionValorInOrden =
                        listaDeValoresInOrden.indexOf(valorDeLaListaNoInOrden);
        
        // dividir las listas en sublistas
        List<K> subListaDeClavesInOrdenPorIzquierda=
                listaDeClavesInOrden.subList(0,posicionClaveInOrden);
        List<K> subListaDeClavesInOrdenPorDerecha=listaDeClavesInOrden.
                subList(posicionClaveInOrden+1,listaDeClavesInOrden.size());
        
        //realizae la misma division para las listas NoInorden y la de valores         
        List<K> subListaDeClavesNoInOrdenPorIzquierda=
                listaDeClavesNoInOrden.subList(1,posicionClaveInOrden+1); 
        List<K> subListaDeClavesNoInOrdenPorDerecha=listaDeClavesNoInOrden.
                   subList(posicionClaveInOrden+1,listaDeClavesInOrden.size()); 
        
        List<V> subListaDeValoresInOrdenPorIzquierda=
                listaDeValoresInOrden.subList(0,posicionClaveInOrden);
        List<V> subListaDeValoresInOrdenPorDerecha=listaDeValoresInOrden.
                subList(posicionClaveInOrden+1,listaDeValoresInOrden.size());
        
        List<V> subListaDeValoresNoInOrdenPorIzquierda=
                listaDeValoresInOrden.subList(1,posicionClaveInOrden+1);
        List<V> subListaDeValoresNoInOrdenPorDerecha=listaDeValoresNoInOrden.
                   subList(posicionClaveInOrden+1,listaDeValoresInOrden.size());
        
      //lamadas recursivas para reconstruir los nodos hijos izquierdo y derecho
      NodoBinario<K,V> nuevoNodoPorIzquierda=this.reconstruirConPreOrden
     (subListaDeClavesInOrdenPorIzquierda,subListaDeClavesNoInOrdenPorIzquierda,
     subListaDeValoresInOrdenPorIzquierda,
                                        subListaDeValoresNoInOrdenPorIzquierda);
   
     NodoBinario<K,V> nuevoNodoPorDerecha=this.reconstruirConPreOrden
     (subListaDeClavesInOrdenPorDerecha,subListaDeClavesNoInOrdenPorDerecha,
       subListaDeValoresInOrdenPorDerecha,subListaDeValoresNoInOrdenPorDerecha);
    
     // construir y devolver el nodoActual
     NodoBinario<K,V> nodoActual=new NodoBinario(claveDeLaListaNoInOrden,
                                                      valorDeLaListaNoInOrden);
     nodoActual.setHijoIzquierdo(nuevoNodoPorIzquierda);
     nodoActual.setHijoDerecho(nuevoNodoPorDerecha);
     return nodoActual;
    }
    
    @Override
     // este metodo inserta un nodo al arbol
    public void insertar(K claveAInsertar, V valorAInsertar) {
     
     // verificamos si la claveAInsertar es nula   
     if(claveAInsertar==null){
      throw new IllegalArgumentException("Excepcion: no puede insertar "
                                     + "claves nulas"); 
     }
     // verificamos si el valorAInsertar es nulo 
     if(valorAInsertar==null){
     throw new IllegalArgumentException("Excepcion: no puede insertar "
                                   + "valores nulos");
     }
     
     // verificamos si el arbol esta vacio
     if(this.esArbolVacio()){ 
      // si lo esta entonces insertamos directo en la raiz
      this.raiz=new NodoBinario(claveAInsertar,valorAInsertar);
      return;
     }
     // buscamos el lugar para insertar
     // el nodoAnterior es nuestro respaldo del nodoActual
     NodoBinario<K,V> nodoAnterior=NodoBinario.nodoVacio();
     NodoBinario<K,V> nodoActual=this.raiz;//es para recorrer el arbol 
     do{
      nodoAnterior=nodoActual;// actualizar el respaldo
      K claveDelNodoActual=nodoActual.getClave(); 
      //compara la claveAInsertar con la claveDelNodoActual
      if(claveAInsertar.compareTo(claveDelNodoActual)>0){
         nodoActual=nodoActual.getHijoDerecho();//mover hacia la derecha
      }else if(claveAInsertar.compareTo(claveDelNodoActual)<0){
       nodoActual=nodoActual.getHijoIzquierdo();//mover hacia la izquierda
       }else{
         // si la claveAInsertar ya existe actualizar el valor correspondiente
         nodoActual.setValor(valorAInsertar);
         return;//salir del metodo 
        }
     }while(!NodoBinario.esNodoVacio(nodoActual));//repetir hasta ser nodoVacio                                      
      
     // insertar el nuevoNodo en la posicion correspondiente
      NodoBinario<K,V> nuevoNodo=new NodoBinario(claveAInsertar,valorAInsertar);
      K claveDelNodoAnterior=nodoAnterior.getClave();
      if(claveAInsertar.compareTo(claveDelNodoAnterior)>0){
         //insertar a la derecha del nodoAnterior  
        nodoAnterior.setHijoDerecho(nuevoNodo);
      }else{
       //insertar a la izquierda del nodoAnterior
        nodoAnterior.setHijoIzquierdo(nuevoNodo);
       }
    }

    @Override
    // metodo para eliminar un nodo del arbol 
    public V eliminar(K claveAEliminar) {
    
    // verifica s la claveAEliminar es nula    
    if(claveAEliminar==null){
      throw new IllegalArgumentException("no se permite clave nulas");  
    }
    // busca el valor asociado a la claveAeliminar
    V valorARetornar=this.buscar(claveAEliminar);
    // verifica si la clave no existe en el arbol 
    if(valorARetornar==null){
      throw new IllegalArgumentException("la clave no existe en el arbol");  
    }
    //eliminar el nodo y actualizar la raiz
    this.raiz=eliminar(this.raiz,claveAEliminar);
    return valorARetornar;// retorna el valor asociado a la claveAEliminar
    }
    
    // metodo protegido para eliminar el un nodo del arbola partir de una clave
    protected NodoBinario<K,V> eliminar(NodoBinario<K,V>nodoActual,K
                                                                claveAEliminar){    
     K claveDelNodoActual=nodoActual.getClave();
     // si la claveAEliminar es mayor a la claveDelNodoActual
     if(claveAEliminar.compareTo(claveDelNodoActual)>0){
        //elimina el nodo del subArbol derecho  
        NodoBinario<K,V> supuestoNuevoHijoDerecho=
                      this.eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        return nodoActual;
     }
     // si la claveAEliminar es menor a la claveDelNodoActual
     if(claveAEliminar.compareTo(claveDelNodoActual)<0){
        //elimina el nodo del subArbol izquierdo   
        NodoBinario<K,V> supuestoNuevoHijoIzquierdo=
                    this.eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
        nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
        return nodoActual;
     }
     // casos de eliminacion cuando se encuentra el nodo con la claveAEliminar
    
     //caso 1: si el nodoActual es una hoja
     if(nodoActual.esHoja()){
      return NodoBinario.nodoVacio();
     }
     //caso 2: si el nodoActual tiene un solo hijo su hijo toma su lugar
     //caso 2.1 : si el nodoActual tiene solo su hijo derecho
     if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
        return nodoActual.getHijoIzquierdo();
     }
     // caso 2.2 : si el nodoActual tiene solo su hijo izquierdo
     if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
      return nodoActual.getHijoDerecho();
     }
    // caso 3 : si el nodoActual tiene sus dos hijos buscamos su sucesor inOrden
    // obtenemos el sucesor inorden 
     NodoBinario<K,V> nodoSucesor=this.getSucesor(nodoActual.getHijoDerecho());
     // eliminamos el sucesor del arbol para reemplazar
     NodoBinario<K,V> supuestoNuevoHijoDerecho=
             this.eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
     // actualizamos el hijo derecho del nodoActual
     nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
     // reemplazamos los datos del nodoActual con los datos del nodoSucesor
     nodoActual.setClave(nodoSucesor.getClave());
     nodoActual.setValor(nodoSucesor.getValor());
     //retornamos el nodoActual ya modificado
     return nodoActual;
   }
    
    // metodo protegido para obtener el sucesor inOrden de un nodo 
    protected NodoBinario<K,V> getSucesor(NodoBinario<K,V> nodoActual){
     //iterar hasta que el hijo izquierdo sea nodoVacio   
     while(!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
         nodoActual=nodoActual.getHijoIzquierdo();
     }   
     return nodoActual;//retornamos el sucesor del nodoActual
    }
    
    @Override
    // este metodo busca la clave por todo el arbol
    public V buscar(K claveABuscar) {
     // verificamos que la claveABuscar sea nula   
     if(claveABuscar==null){
       throw new IllegalArgumentException("no se puede buscar claves nulas ");  
     }
     //verificamos si el arbol esta vacio
     if(this.esArbolVacio()){
        return null;
     }
     // iniciar la busqueda desde la raiz del arbol
     NodoBinario<K,V> nodoActual=this.raiz; 
     do{   
      K claveDelNodoActual=nodoActual.getClave();
      // comparamos la claveABuscar con la claveDelNodoActual son iguales 
      if(claveABuscar.compareTo(claveDelNodoActual)==0){
         //retornamos el valor asociado  
         return nodoActual.getValor();
      }   
      //si la claveABuscar es mayor a la claveDelNodoActual
      if(claveABuscar.compareTo(claveDelNodoActual)>0){  
        nodoActual=nodoActual.getHijoDerecho();// nos movemos hacia la derecha
      }else{
        //si la claveABuscar es menor a la claveDelNodoActual  
        nodoActual=nodoActual.getHijoIzquierdo();//nos movemos a la izquierda
      }
      //repetir hasta que encuentre el valor o  el nodoActual sea nodoVacio
     }while(!NodoBinario.esNodoVacio(nodoActual));
     return null; 
    }

    @Override
    // este metodo revisa si ya existe la clave en el arbol o no
    public boolean contiene(K claveABuscar) {
     //revisar si la laveABuscar es nula
     if(claveABuscar==null){
      throw new IllegalArgumentException("nose puede usar claves nulas");    
     }
     //llamamos a un metodo privado y retornamos su valor
     return contiene(this.raiz,claveABuscar);
    }
    
    //metodo privado que revisa si la clave esta en el arbol
    private boolean contiene(NodoBinario<K,V> nodoActual,K claveABuscar){
      // revisamos si el nodoActual esNodoVacio    
     if(NodoBinario.esNodoVacio(nodoActual)){
        return false; 
     }
     // revisamos si el nodoActual esHoja
     if(nodoActual.esHoja()){
      // comapramos con la claveABuscar si son iguales   
      return nodoActual.getClave().compareTo(claveABuscar)==0;
     }
     // hacemos las llamdas recursivas para ver si esta en los subArboles 
     boolean contienePorIzquierda=contiene(nodoActual.getHijoIzquierdo(),
                                          claveABuscar);
     boolean contienePorDerecha=contiene(nodoActual.getHijoDerecho(),
                                         claveABuscar);
     // revisamos si la claveABuscar estaba en unos de los subArboles
     if(contienePorIzquierda || contienePorDerecha){ 
        return true;
     }
     //comparamos si la clave de la raiz es igual a la claveABuscar
     return nodoActual.getClave().compareTo(claveABuscar)==0;
    }
    //NOTA: se puede simplificar el metodo haciendo una llamada al metodo buscar
    // y ver si lo que devuelve es diferente a null
    @Override
    // este metodo devuelve la cantidad de nodos en el arbol 
    public int size() { //version iterativa
    int contadorDeNodos=0; // creamos un  contador
    //verificamos que el arbol no este vacio
    if(!this.esArbolVacio()){
     // inicializamos una pila para almacenar todos los nodos que visitemos   
     Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
     // insertamos la raiz del arbol en la pila para iniciar el recorrido
     pilaDeNodos.push(this.raiz);
     do{
     NodoBinario<K,V> nodoActual=pilaDeNodos.pop();// sacar el nodo de la pila
     contadorDeNodos++;// y se incrementa el contador
     //verificamos si tiene hijo derecho 
     if(!nodoActual.esVacioHijoDerecho()){
       // si lo tiene lo insertamos a la pila  
       pilaDeNodos.push(nodoActual.getHijoDerecho());
     }
     //verificamos si tiene hijo izquierdo
     if(!nodoActual.esVacioHijoIzquierdo()){
       // si lo tiene insertamos a la pila  
       pilaDeNodos.push(nodoActual.getHijoIzquierdo());
     }
     }while(!pilaDeNodos.isEmpty());//iterar hasta que la pila este vacia
    }
    return contadorDeNodos;// retornamos el contador  
    }

    @Override
     // este metodo calcula la altura que tiene el arbol
    public int altura() {   
     // llamamos a ub metodo protegido   
     return altura(this.raiz); //retornamos el valor que devuelva la llamada
    }
    // este metodo protegido devuelve la altura de un nodo
    protected int altura(NodoBinario<K,V> nodoActual){
     // verificamos si el nodo es vacio   
     if(NodoBinario.esNodoVacio(nodoActual)){
       return 0;  
     }   
     //verificamos si el nodo es una hoja
     if(nodoActual.esHoja()){
       return 1;  
     }
     // hacemos las llamadas recursivas
     int alturaPorIzquierda=altura(nodoActual.getHijoIzquierdo());
     int alturaPorDerecha=altura(nodoActual.getHijoDerecho());
     //verificamos si que altura es mas larga y retornamos esa altura+1 
     return alturaPorIzquierda>alturaPorDerecha ? alturaPorIzquierda+1
                                                :alturaPorDerecha+1;
    }

    @Override
    //este metodo limpia el arbol dejandolo vacio
    public void vaciar(){
     this.raiz=NodoBinario.nodoVacio();
    }

    @Override
    //este metodo verifica si el arbol esta vacio o no
    public boolean esArbolVacio() {
     return NodoBinario.esNodoVacio(raiz);
    }

    @Override
    // este metodo calcula el nivel que tiene el arbol
    public int nivel() {    
     //lmamos a un metodo privado   
     return nivel(this.raiz);// retornamos el resultado del metodo que llamo
    }
    
    // metodo privado que devuelve el nivel de un nodo
    private int nivel(NodoBinario<K,V> nodoActual){
     //verifica si el nodoActual es un nodoVacio   
     if(NodoBinario.esNodoVacio(nodoActual)){ 
        return -1; 
     } 
     // verifica si el nodoActual es una hoja
     if(nodoActual.esHoja()){
        return 0;  
     }
     // hacemos las llamadas recursivas
     int nivelPorIzquierda=nivel(nodoActual.getHijoIzquierdo()); 
     int nivelPorDerecha=nivel(nodoActual.getHijoDerecho());
     // compara el nivel mas alto y retorna el nivel mas alto+1
     return nivelPorIzquierda>nivelPorDerecha ? nivelPorIzquierda+1
                                             : nivelPorDerecha+1;
    }

    @Override
    // este metodo realiza un recorrido a profundidad del arbol y retorna una
    // lista con todas las claves que visito
    public List<K> recorridoEnInOrden() {  
     // lista para almacenar las claves visitadas durante el recorrido   
     List<K> recorrido=new ArrayList<>(); 
     // creamos una pila para almacenar los nodos que se van recorriendo
     Stack<NodoBinario<K,V>>pilaDeNodos=new Stack<>();
     //verificar si el arbol no esta vacio
     if(!this.esArbolVacio()){    
      // iniciamos desde la raiz del arbol   
      NodoBinario<K,V> nodoActual=this.raiz;
       // itera hasta que el nodoActual sea vacio y la pila este vacia
      while(!NodoBinario.esNodoVacio(nodoActual)||!pilaDeNodos.isEmpty()){
        //llamamos aun metodo privado  
       this.meterNodosAlaPilaInOrden(nodoActual, pilaDeNodos);
       // sacar un nodo de la pila para procesarlo
       nodoActual=pilaDeNodos.pop();
       K claveDelNodoActual=nodoActual.getClave();
       // agragamos la clave al recorrido
       recorrido.add(claveDelNodoActual);
       //actualizamos la referencia
       nodoActual=nodoActual.getHijoDerecho();
      }
     }
     return recorrido;
    }

    //metodo privado que mete nodos a la pila del recorrido inOrden
    private void meterNodosAlaPilaInOrden(NodoBinario<K,V> nodoActual,
                                          Stack<NodoBinario<K,V>>pilaDeNodos){
     // itera hasta que nodoActual sea nodoVacio    
     while(!NodoBinario.esNodoVacio(nodoActual)){
       //metemos el nodoActual a la pila  
       pilaDeNodos.push(nodoActual); 
       //actualiza su referencia
       nodoActual=nodoActual.getHijoIzquierdo();
      }    
    }
    
    @Override
    // este metodo realiza un recorrido por profundidad de todo el arbol y 
    // y retorna una lista de las claves que visito
    public List<K> recorridoEnPreOrden() {
     // lista para almacenar las claves visitadas durante el recorrido
     List<K> recorrido=new ArrayList<>();
     // verificar si el arbol no esta vacio
     if(!this.esArbolVacio()){
     // crea una pila para almacenar los nodos que visitemos    
     Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
     // insertamos la raiz
     pilaDeNodos.push(this.raiz);
     do{
      // sacamos un nodo de la pila   
      NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
      K claveDelNodoActual=nodoActual.getClave();
      // guadarmos la clave del nodoActual en el recorrido
      recorrido.add(claveDelNodoActual);
       // verificamos si el nodoActual tiene hijo derecho
       if(!nodoActual.esVacioHijoDerecho()){
         //si lo tiene lo inserta en la pila  
         pilaDeNodos.push(nodoActual.getHijoDerecho());
       }
       // verifica si el nodoActual tiene hijoIzquierdo
       if(!nodoActual.esVacioHijoIzquierdo()){
          //si lo tiene que lo inserta en la pila
         pilaDeNodos.push(nodoActual.getHijoIzquierdo());
       }
     }while(!pilaDeNodos.isEmpty()); // itera hasta que la pila este vacia
    }
    return recorrido; // retornamos la lista 
    }
    
    @Override
    // este algoritmo lo que hace es recorrer en profundidad el arbol
    // y devuelve una lista con las claves que visito 
    public List<K> recorridoEnPostOrden() { //version del inge //
    // lista para almacenar las claves visitadas durante el recorrido     
    List<K> recorrido=new ArrayList<>();
     // verifica si el arbol no esta vacio
    if(!this.esArbolVacio()){
      // creamos una pila para almacenar los nodos que se van visitando  
      Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>(); 
      //llamamos a un metodo privado
      this.meterAlaPilaParaPostOrden(raiz,pilaDeNodos);
      do{
        //sacamos un nodo de la pila  
        NodoBinario<K,V> nodoActual=pilaDeNodos.pop();
        K claveDelNodoActual=nodoActual.getClave();
        // agregamos la clave del nodo actual a la lista de recorrido
        recorrido.add(claveDelNodoActual);
         //verificamos si la pila no esta vacia
        if(!pilaDeNodos.isEmpty()){ 
           // obtenemos el nodo que esta en el tope de la pila 
           NodoBinario<K,V> nodoDelTopeDeLaPila=pilaDeNodos.peek();
           //si el hijo izquierdo del nodoDelTopeDeLaPila es igual al nodoActual
           if(nodoDelTopeDeLaPila.getHijoIzquierdo()==nodoActual){
             // llamamos al metodo privado para meter a la pila los nodos 
             //del hijo derecho
             this.meterAlaPilaParaPostOrden(nodoDelTopeDeLaPila.getHijoDerecho()
                                            ,pilaDeNodos);
           }
        }
      }while(!pilaDeNodos.isEmpty());// iterar hasta que la pila este vacia
    }
    return recorrido; // retornamos la lista 
    }
    
    // este metodo privado ayuda metiendo nodos para el recorrido en postorden
    private void meterAlaPilaParaPostOrden(NodoBinario<K,V> nodoActual,
                                           Stack<NodoBinario<K,V>>pilaDeNodos){
        //itera hasta que el nodoActual sea nodoVacio
        while(!NodoBinario.esNodoVacio(nodoActual)){
        //se mete el nodoActual a la pila    
        pilaDeNodos.push(nodoActual); 
        if(!nodoActual.esVacioHijoIzquierdo()){     
          // actualiza la referencia al de su hijo izquierdo  
          nodoActual=nodoActual.getHijoIzquierdo(); 
        }else{
           // actualiza la referencia al de su hijo derecho  
         nodoActual=nodoActual.getHijoDerecho(); 
        }
      }    
    }
    
    @Override
    //este metodo realiza un recorrido por amplitud de todo el arbol
    // y devuelve una lista con las claves que visito
    public List<K> recorridoPorNiveles() {
     // lista para almacenar las claves visitadas durante el recorrido    
     List<K> recorrido= new ArrayList<>();
     // creamos una cola para almacenar los nodos del arbol
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
     // insertamos la raíz del árbol en la cola para comenzar el recorrido
     colaDeNodos.offer(raiz); //metimos el primer nodo
     do{
     // sacamos el nodo de la cola     
     NodoBinario<K,V> nodoActual=colaDeNodos.poll();
     K claveDelNodoActual= nodoActual.getClave();
     // agregamos la clave del nodoActual a la lista de recorrido
     recorrido.add(claveDelNodoActual);// metemos la clave a la lista
      // verificamos si el nodoActual tiene hijo izquierdo
     if(!nodoActual.esVacioHijoIzquierdo()){
       // si lo tiene lo metemos a la cola  
       colaDeNodos.offer(nodoActual.getHijoIzquierdo());
     }
     // verificamos si el nodoActual tiene hijo derecho
     if(!nodoActual.esVacioHijoDerecho()){
       // si tiene metemos a la cola   
       colaDeNodos.offer(nodoActual.getHijoDerecho());
     }   
     }while(!colaDeNodos.isEmpty());// itrea hasta que la cola este vacia
     return recorrido; // retorna la lista 
    }
   //NOTA: todos los recorridos a excepcion del por nivels se pueden 
    //     hacer recursivo 

    //este metodo imprime de manera vertial el arbol  
    public void  imprimir(JTextArea pizarra){
      //llamamos a un metodo privado  
     imprimir(this.raiz,pizarra);   
    }
    // este metodo imprime de manera vertical un nodo
    private void imprimir(NodoBinario<K,V> nodoActual,JTextArea pizarra){
     //verificamos si el nodoActual esta vacio
     if(NodoBinario.esNodoVacio(nodoActual)){
       return; //salimos del metodo   
     }
     // verificar si el nodoActual es una hoja
     if(nodoActual.esHoja()){
       pizarra.append("("+nodoActual.getClave().toString()+")"); 
      return;
     }
     //ponemos la clave del nodoActual en la pizarra
     pizarra.append("("+(nodoActual.getClave().toString())+")");
     //verificamos si tiene hijo izquierdo
     if(!nodoActual.esVacioHijoIzquierdo()){
       pizarra.append("       \n |--(I) ");
       //hacemos la llamada recursiva
       this.imprimir(nodoActual.getHijoIzquierdo(),pizarra);
     }
     //verificamos si tiene hijo derecho
     if(!nodoActual.esVacioHijoDerecho()){
       pizarra.append("      \n |--(D) ");
       // hacemos la llamada recursiva
       this.imprimir(nodoActual.getHijoDerecho(), pizarra);
     } 
    }  
   // EJERCICIOS DEL INGENIERO
   
  // tarea 1: contar cuantos nodos tienen 2 hijos diferentes de vacios
   public int cantidadDeNodosDeDosHijos(){
    return cantidadDeNodosDeDosHijos(this.raiz);      
   } 
   // metodo privado que cuenta cuantos nodos tienen 2 hijos
   private int cantidadDeNodosDeDosHijos(NodoBinario<K,V> nodoActual){
     int contadorDeNodos=0;  
     //verificamos si el nodoActual no esta vacio o es una hoja
     if(NodoBinario.esNodoVacio(nodoActual)|| nodoActual.esHoja()){
       return contadorDeNodos;
     }
     // verificamos si el ndoActual tiene los dos hijos
     if(!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
      contadorDeNodos++; //incrementamos el contador   
     }
     //hacemos las llamadas recursivas
     contadorDeNodos+=
      this.cantidadDeNodosDeDosHijos(nodoActual.getHijoIzquierdo())+
      this.cantidadDeNodosDeDosHijos(nodoActual.getHijoDerecho());
     // retornamos la cantidad de nodos 
    return contadorDeNodos;    
   }
  //tarea 2: cuantos hijos vacioshay en el arbol
   public int cantidadDeHijosVacios(){
    return cantidadDeHijosVacios(this.raiz);
   }   
   // este metodo privado cuenta cuantos hijos vacios tiene un nodo
   private int cantidadDeHijosVacios(NodoBinario<K,V> nodoActual){
    int contadorDeNodosVacios=0;
    //verificamos si el nodoActual es nodoVacio
    if(NodoBinario.esNodoVacio(nodoActual)){
      return 1;  
    }
    //verificamos si el nodoActual es hoja
    if(nodoActual.esHoja()){
       return 2; 
    }
    // verificamos si el nodoActual tiene sus dos hijos
    if(!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
      return 0;
    }
    // hacemos las llamadas recursivas
    contadorDeNodosVacios=
      this.cantidadDeHijosVacios(nodoActual.getHijoIzquierdo())+
            this.cantidadDeHijosVacios(nodoActual.getHijoDerecho());
    // retornamos la cantidad de nodos vacios
    return contadorDeNodosVacios; 
    }
     //tarea 1: saber si el arbol esta balanceado
    public boolean estaBalanceado(){
     // creamos una cola para almacenar los nodos del arbol
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
     // insertamos la raíz del árbol en la cola para comenzar el recorrido
     colaDeNodos.offer(raiz); //metimos el primer nodo
     do{
     // sacamos el nodo de la cola     
     NodoBinario<K,V> nodoActual=colaDeNodos.poll();
      // verificamos si el nodoActual tiene hijo izquierdo
     if(!nodoActual.esVacioHijoIzquierdo()){
       // si lo tiene lo metemos a la cola  
       colaDeNodos.offer(nodoActual.getHijoIzquierdo());
     }
     // verificamos si el nodoActual tiene hijo derecho
     if(!nodoActual.esVacioHijoDerecho()){
       // si tiene metemos a la cola   
       colaDeNodos.offer(nodoActual.getHijoDerecho());
     } 
      int alturaPorIzquierda=this.altura(nodoActual.getHijoIzquierdo());
      int alturaPorDerecha=this.altura(nodoActual.getHijoDerecho());
      int diferenciaDeAlturas=alturaPorIzquierda-alturaPorDerecha;
      if(diferenciaDeAlturas!=AVL.RANGO_SUPERIOR||diferenciaDeAlturas!=0 ||
         diferenciaDeAlturas!=AVL.RANGO_INFERIOR){
         return false;  
      }
     }while(!colaDeNodos.isEmpty());// itrea hasta que la cola este vacia
     return true; 
    }
    public V eliminarIterativo(K claveAEliminar){
     if(claveAEliminar==null){
      throw new IllegalArgumentException("no se permite clave nulas");  
    }
    if(this.contiene(claveAEliminar)){
      V valorARetornar=this.buscar(claveAEliminar);
      NodoBinario<K,V> nodoActual=this.raiz;
      Stack<NodoBinario<K,V>> pilaDeNodos=new Stack<>();
      K claveDelNodoActual=nodoActual.getClave();
      while(claveDelNodoActual.compareTo(claveAEliminar)!=0){
        pilaDeNodos.push(nodoActual);
        if(claveAEliminar.compareTo(claveDelNodoActual)<0){
          nodoActual=nodoActual.getHijoIzquierdo();
        }else if(claveAEliminar.compareTo(claveDelNodoActual)>0){
          nodoActual=nodoActual.getHijoDerecho();
         }
        claveDelNodoActual=nodoActual.getClave();
      }
      // revisar los casos  caso 1
      if(pilaDeNodos.isEmpty() && nodoActual.esHoja()){
         this.vaciar();    
         return valorARetornar; 
      }else if(pilaDeNodos.isEmpty() && !nodoActual.esVacioHijoIzquierdo()
                                             &&nodoActual.esVacioHijoDerecho()){    
        this.raiz=nodoActual.getHijoIzquierdo();
        return valorARetornar;
       }else if(pilaDeNodos.isEmpty() && nodoActual.esVacioHijoIzquierdo()
                                            &&!nodoActual.esVacioHijoDerecho()){    
         this.raiz=nodoActual.getHijoDerecho();
         return valorARetornar; 
       }  
       NodoBinario<K,V> nodoAnterior = pilaDeNodos.pop();
       if(nodoAnterior.getHijoIzquierdo()==nodoActual && nodoActual.esHoja()){
          nodoAnterior.setHijoIzquierdo(NodoBinario.nodoVacio());
          return valorARetornar;
       }
       if(nodoAnterior.getHijoDerecho()==nodoActual && nodoActual.esHoja()){
         nodoAnterior.setHijoDerecho(NodoBinario.nodoVacio());
         return valorARetornar;
       }
     // caso 2
      if(!nodoActual.esVacioHijoIzquierdo()&&nodoActual.esVacioHijoDerecho()){    
        nodoAnterior.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
        return valorARetornar;
      }else if(nodoActual.esVacioHijoIzquierdo()
                                            &&!nodoActual.esVacioHijoDerecho()){    
        nodoAnterior.setHijoDerecho(nodoActual.getHijoDerecho());
        return valorARetornar; 
       }
       Stack<NodoBinario<K,V>>pilaDelSucesor=new Stack<>();
       NodoBinario<K,V> nodoSucesor=obtenerSucesor
                                   (nodoActual.getHijoDerecho(),pilaDelSucesor);
       K claveDelSucesor =nodoSucesor.getClave();
       V valorDelSucesor =nodoSucesor.getValor();
       nodoActual.setClave(claveDelSucesor);
       nodoActual.setValor(valorDelSucesor);
       if(pilaDelSucesor.isEmpty()){
           nodoActual.setHijoDerecho(NodoBinario.nodoVacio());
           return valorARetornar;
       }
       NodoBinario<K,V> nodoPadreDelSucesor=pilaDelSucesor.pop();
       if(nodoSucesor.esHoja()){
          nodoPadreDelSucesor.setHijoIzquierdo(NodoBinario.nodoVacio());
          return valorARetornar;
       }
       // caso 2
      if(nodoSucesor.esVacioHijoIzquierdo()&&!nodoSucesor.esVacioHijoDerecho()){    
        nodoPadreDelSucesor.setHijoIzquierdo(nodoSucesor.getHijoDerecho());
        return valorARetornar;
      }
    } 
    return null;
  }
  private NodoBinario<K,V> obtenerSucesor(NodoBinario<K,V> nodoActual,
                                           Stack<NodoBinario<K,V>> pilaDeNodos){
  while(!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
    pilaDeNodos.push(nodoActual);
    nodoActual=nodoActual.getHijoIzquierdo();
  }
  return nodoActual;
  }
}  