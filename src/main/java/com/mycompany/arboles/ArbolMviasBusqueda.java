/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arboles;

import Excepciones.OrdenInvalidoException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */
public class ArbolMviasBusqueda<K extends Comparable<K>,V> implements
                                                            IArbolBusqueda<K,V>{
    protected NodoMvias<K,V> raiz;
    protected int orden;
    public static final int ORDEN_MINIMO=3;
    public static final int POSICION_INVALIDA=-1;

    
    public ArbolMviasBusqueda() {
    this.orden=ArbolMviasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMviasBusqueda(int orden)throws OrdenInvalidoException{
     if(orden<ArbolMviasBusqueda.ORDEN_MINIMO){   
      throw new OrdenInvalidoException();
     } 
     this.orden = orden;
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
      this.raiz=new NodoMvias(this.orden,claveAInsertar,valorAInsertar);
      return;
     }       
     NodoMvias<K,V> nodoActual=this.raiz;// es para recorrer el arbol
     do{
      int posicionDeLaClaveAInsertar=this.buscarPosicionDeClave(claveAInsertar,
                                                                nodoActual);
      // verificamos si la claveAInsertar esta o no en el arbol
      if(posicionDeLaClaveAInsertar!=ArbolMviasBusqueda.POSICION_INVALIDA){
       // actualizamos el valor en la posicion que encontramos
       nodoActual=this.buscarNodo(nodoActual,claveAInsertar);
       nodoActual.setValor(posicionDeLaClaveAInsertar,valorAInsertar);
       nodoActual=NodoMvias.nodoVacio();
      }else if(nodoActual.esHoja()){
       //caso contrario la claveAInsertar no esta en el arbol y verificamos que 
       // el nodoActual sea una hoja
       if(nodoActual.clavesLlenas()){
         int posicionPorDondeBajar=this.buscarPosicionDondeBajar(nodoActual,
                                                                claveAInsertar); 
          NodoMvias<K,V> nuevoNodo= new NodoMvias(orden,claveAInsertar,
                                                                valorAInsertar);
         nodoActual.setHijo(posicionPorDondeBajar,nuevoNodo);  
       }else{
         this.insertarClaveYValorOrdenado(nodoActual,claveAInsertar,
                                                                valorAInsertar);
       }
       nodoActual=NodoMvias.nodoVacio();
      }else{
        // el nodoActual no es una hoja y ya sabemos que la clave no existe
        // buscamos la posicion por donde bajar  
        int posicionPorDondeBajar=this.buscarPosicionDondeBajar(nodoActual,
                                                                claveAInsertar);
        if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
            NodoMvias<K,V> nuevoNodo= new NodoMvias(orden,claveAInsertar,
                                                                valorAInsertar);
         nodoActual.setHijo(posicionPorDondeBajar,nuevoNodo);
         nodoActual=NodoMvias.nodoVacio();
        }else{
          nodoActual=nodoActual.getHijo(posicionPorDondeBajar); 
        }
     }        
     }while(!NodoMvias.esNodoVacio(nodoActual));
     //itera hasta que el nodoActual sea vacio
    }
    
    protected int buscarPosicionDeClave(K claveABuscar,
                                                     NodoMvias<K,V> nodoActual){
     // caso base: si es que el nodoActual esNodoVacio o si la clave no existe
     //en el arbol
     if(NodoMvias.esNodoVacio(nodoActual)|| !this.contiene(claveABuscar)){
         // retornamos posicion invalida
        return ArbolMviasBusqueda.POSICION_INVALIDA; 
     }
     // caso base: si el nodoActual es una hoja
     if(nodoActual.esHoja()){
      // hacemos un for para ver sus claves
      for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
       K claveDelNodoActual=nodoActual.getClave(i);
       // comparamos la claveABuscar con la claveDelNodoActual si son iguales
       if(claveABuscar.compareTo(claveDelNodoActual)==0){
         // retornamos la posicion en donde estaba
          return i;
       }
      }// salimos del for 
      //si no lo encontro retorna posicion ivalida
      return ArbolMviasBusqueda.POSICION_INVALIDA;
     }
     // hacemos un for para buscar la clave 
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){   
        K claveDelNodoActual=nodoActual.getClave(i);
        // comparamos la claveABuscar con la claveDelNodoActual si son iguales
        if(claveABuscar.compareTo(claveDelNodoActual)==0){// revisar de nuevo
         // retornamos la posicion en donde estaba
          return i;
        }
        //si no son iguales comparamos si la claveABuscar es menor al del nodo 
        if(claveABuscar.compareTo(claveDelNodoActual)<0){  
         return this.buscarPosicionDeClave(claveABuscar,nodoActual.getHijo(i));
        }  
      }// fin del for
     //verificamos si en la ultimo hijo tiene la claveABuscar o no y retornamos
     return this.buscarPosicionDeClave(claveABuscar,
                          nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
    }   
    protected int buscarPosicionDondeBajar(NodoMvias<K,V> nodoActual,
                                                                K claveABuscar){
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
      K claveDelNodoActual=nodoActual.getClave(i);
      if(claveABuscar.compareTo(claveDelNodoActual)<0){
         return i; 
      }
     }
     return nodoActual.nroDeClavesNoVacias();
    }
    protected NodoMvias<K,V>buscarNodo(NodoMvias<K,V> nodoActual,K 
                                                               claveABuscar){
     if(NodoMvias.esNodoVacio(nodoActual)){
        return NodoMvias.nodoVacio();
     }
     if(nodoActual.esHoja()){
       for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
         K claveDelNodoActual=nodoActual.getClave(i);
         if(claveDelNodoActual.compareTo(claveABuscar)==0){
           return nodoActual;  
         }
       }
       return NodoMvias.nodoVacio();
     }
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
       K claveDelNodoActual=nodoActual.getClave(i);
       if(claveDelNodoActual.compareTo(claveABuscar)==0){
          return nodoActual;  
       }
     }
     int posicionParaBajar=this.buscarPosicionDondeBajar
                                                     (nodoActual,claveABuscar);
     return this.buscarNodo(nodoActual.getHijo(posicionParaBajar),claveABuscar);
    }         
    protected void insertarClaveYValorOrdenado(NodoMvias<K,V> nodoActual,
                                             K claveAInsertar,V valorAInsertar){
     nodoActual.setClave(nodoActual.nroDeClavesNoVacias(),claveAInsertar);
     nodoActual.setValor(nodoActual.nroDeClavesNoVacias(),valorAInsertar);
     for(int i=0;i<(nodoActual.nroDeClavesNoVacias()-1);i++){
      for(int j=0;j<(nodoActual.nroDeClavesNoVacias()-1);j++){
       K claveActual=nodoActual.getClave(j);
       V valorActual=nodoActual.getValor(j);
       K claveSiguiente=nodoActual.getClave(j+1);
       V valorSiguiente=nodoActual.getValor(j+1);
       if(claveActual.compareTo(claveSiguiente)>0){
         nodoActual.setClave(j,claveSiguiente);
         nodoActual.setValor(j,valorSiguiente);
         nodoActual.setClave((j+1),claveActual);
         nodoActual.setValor((j+1),valorActual);
       }
      }   
     }
    }
    
    @Override
       // metodo para eliminar un nodo del arbol 
    public V eliminar(K claveAEliminar) {
    
    // verifica s la claveAEliminar es nula    
    if(claveAEliminar==(K)NodoMvias.datoVacio()){
      throw new IllegalArgumentException("no se permite clave nulas");  
    }
    // busca el valor asociado a la claveAeliminar
    V valorARetornar=this.buscar(claveAEliminar);
    // verifica si la clave no existe en el arbol 
    if(valorARetornar==(V) NodoMvias.datoVacio()){
      throw new IllegalArgumentException("la clave no existe en el arbol");  
    }
    //eliminar el nodo y actualizar la raiz
    this.raiz=eliminar(this.raiz,claveAEliminar);
    return valorARetornar;// retorna el valor asociado a la claveAEliminar
    }
    
    private NodoMvias<K,V> eliminar(NodoMvias<K,V> nodoActual,K claveAEliminar){
     // hacemos un for para recorrer las claves del nodoActual
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
      K claveEnTurno = nodoActual.getClave(i);
      // verificamos si la claveEnTurno es igual a la claveAEliminar
      if(claveAEliminar.compareTo(claveEnTurno)==0){
        //verificamos si el nodo es una hoja  
        if(nodoActual.esHoja()){
          //llamamos aun metodo privado para que borre la claveAEliminar y valor  
          this.eliminarClaveDeNodoDePosicion(nodoActual,i); 
          // verificamos si el nodo esta vacio
          if(!nodoActual.hayClavesNoVacias()){
             // si esta vacio retornamos un nodo vacio  
             return NodoMvias.nodoVacio();
          }
          // si tiene claves retornamos el nodoActual
          return nodoActual;
        }
        // si llego aca el nodoActual no es hoja
        K claveDeReemplazo;// creamos una clave de reemplazo
        // verificamos si tiene hijos a partir de esa posicion
        if(this.hayHijosMasAdelanteDeLaPosicion(nodoActual,i)){
         // aoplicamos el caso 2: hay hijos mas adelante    
         claveDeReemplazo=this.obtenerSucesorInOrden(nodoActual,claveAEliminar);  
        }else{
         //aplicamos el caso 3 : hay hijos de esa posicion para atras   
         claveDeReemplazo=this.obtenerPredecesorInOrden(nodoActual,
                                                                claveAEliminar);
        }
        V valorDeReemplazo=this.buscar(claveDeReemplazo);
        nodoActual=this.eliminar(nodoActual,claveDeReemplazo);
        nodoActual.setClave(i,claveDeReemplazo);
        nodoActual.setValor(i,valorDeReemplazo);
        return nodoActual;
      }
      if(claveAEliminar.compareTo(claveEnTurno)<0){
        NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo(i),
                                                                claveAEliminar);
        nodoActual.setHijo(i,supuestoNuevoHijo);
        return nodoActual;
      }
     }// fin del for
       NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo
                             (nodoActual.nroDeClavesNoVacias()),claveAEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(),supuestoNuevoHijo);
        return nodoActual;
    }
    // este metodo elimina una clave y por consecuencia su valor   
    protected void eliminarClaveDeNodoDePosicion(NodoMvias<K,V> nodoActual,
                                                           int posicionDelDato){
     // borrarmos la clave y el valor que estaban en esa posicion   
     nodoActual.setClave(posicionDelDato,(K) NodoMvias.datoVacio()); 
     nodoActual.setValor(posicionDelDato,(V) NodoMvias.datoVacio());
     // enonces partimos de la posicion del datos que borramos hasta las claves
     // no vacia que tenga el nodo
     for(int i=posicionDelDato;i<nodoActual.nroDeClavesNoVacias();i++){
       //verificamos que la clave de la posicion i es vacio para cambiar
         if(nodoActual.getClave(i)==(K)NodoMvias.datoVacio()){          
           K claveDeLaSiguientePosicion=nodoActual.getClave(i+1);
           V valorDeLaSiguientePosicion=nodoActual.getValor(i+1);
           // movemos el vacio a la siguiente posicion
           nodoActual.setClave(i,claveDeLaSiguientePosicion);
           nodoActual.setValor(i,valorDeLaSiguientePosicion);
           nodoActual.setClave(i+1,(K) NodoMvias.datoVacio());
           nodoActual.setValor(i+1,(V) NodoMvias.datoVacio());
         }   
     }
    }
    // metodo protegido busca de esa posicion+1 si hay hijos mas adleante   
    protected boolean hayHijosMasAdelanteDeLaPosicion(NodoMvias<K,V>nodoActual,
                                                           int posicionInicial){ 
    //recorremos apartir de la posicion que recibimos +1 hasta el orden del nodo    
     for(int i=(posicionInicial+1);i<this.orden;i++){
       // verificamos si hay un hijo no vacio en la posicion i  
       if(!nodoActual.esHijoVacio(i)){
          return true; 
       }  
     }
     return false;
    }
    // este metodo busca la clave del sucesor inorden de la claveABuscar
    protected K obtenerSucesorInOrden(NodoMvias<K,V> nodoActual, K claveABuscar){
     /*buscamos la posicion de la clave en el nodoActual    
     int posicionDeLaClaveABuscar =
                          this.buscarPosicionDeClave(claveABuscar,nodoActual)+1;
     // y luego guardamos su hijo que tiene en dicha posicion 
     NodoMvias<K,V> hijoDelNodoActual=
                                   nodoActual.getHijo(posicionDeLaClaveABuscar);
     // iteramos hasta que el hijo del nodo que guardamos no tenga hijos 
     // en la ultima posicion 
     while(!NodoMvias.esNodoVacio(hijoDelNodoActual.getHijo(0))){
        // si entra guardamos el hijo que estaba en la primera posicion 
        hijoDelNodoActual=
             hijoDelNodoActual.getHijo(0); 
     }
     // al final retornamos la primera clave del hijoDelNodoActual
     return hijoDelNodoActual.getClave(0);*/
     List<K> listaDeClaves=new ArrayList<>();
     this.recorridoEnInOrden(nodoActual,listaDeClaves);
     int posDeLaClave=listaDeClaves.indexOf(claveABuscar)+1;
     return listaDeClaves.get(posDeLaClave);
    }        
    //este metodo busca el predecesor inorden de la claveABuscar
    protected K obtenerPredecesorInOrden(NodoMvias<K,V> nodoActual,
                                                                K claveABuscar){
     //buscamos la posicion de la clave en el nodoActual    
 /*    int posicionDeLaClaveABuscar =
                            this.buscarPosicionDeClave(claveABuscar,nodoActual);
     // y luego guardamos su hijo que tiene en dicha posicion 
     NodoMvias<K,V> hijoDelNodoActual=
                                   nodoActual.getHijo(posicionDeLaClaveABuscar);
     // iteramos hasta que el hijo del nodo que guardamos no tenga hijos 
     // en la ultima posicion 
     while(!NodoMvias.esNodoVacio(hijoDelNodoActual.getHijo
                                    (hijoDelNodoActual.nroDeClavesNoVacias()))){
        // si entra guardamos el hijo que estaba en la ultima posicion 
        hijoDelNodoActual=
             hijoDelNodoActual.getHijo(hijoDelNodoActual.nroDeClavesNoVacias()); 
     }
     // al final retornamos la ultima clave del hijoDelNodoActual
     return hijoDelNodoActual.getClave
                                    (hijoDelNodoActual.nroDeClavesNoVacias()-1);
    }*/
   List<K> listaDeClaves=new ArrayList<>();
     this.recorridoEnInOrden(nodoActual,listaDeClaves);
     int posDeLaClave=listaDeClaves.indexOf(claveABuscar)-1;
     return listaDeClaves.get(posDeLaClave);
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
     NodoMvias<K,V> nodoActual=this.raiz; 
     do{   
       boolean cambiarNodo=false;//esto es para saber si estamos cambiando nodo
       //hacemos un for de todas las claves que tiene el nodoActual 
       // y si no cambio de nodo
       int nroDeClavesNoVacias=nodoActual.nroDeClavesNoVacias();
       for(int i=0;(i<nroDeClavesNoVacias&&!cambiarNodo);i++){    
        K claveDelNodoActual=nodoActual.getClave(i);
        // comparamos la claveABuscar con la claveDelNodoActual son iguales 
        if(claveABuscar.compareTo(claveDelNodoActual)==0){
          //retornamos el valor asociado  
          return nodoActual.getValor(i);
        } 
        //si la claveABuscar es menor a la claveDelNodoActual 
        if(claveABuscar.compareTo(claveDelNodoActual)<0){
           // y decimos que cambiamos de nodos 
           cambiarNodo=true;  
           nodoActual=nodoActual.getHijo(i);
        }
       }// necesiita de este bucle para procesar un nodo
     // saliendo del for revisamos si no cambio de nodo  
     if(!cambiarNodo){
       // nos movemos al hijo en la posicion del nroDeClavesNoVacias  
       nodoActual=nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
     }
      //repetir hasta que encuentre el valor o  el nodoActual sea nodoVacio
     }while(!NodoMvias.esNodoVacio(nodoActual));
     //retornamos esto si nunca lo encontramos
     return null; 
    }

    @Override
    // este metodo revisa si ya existe la clave en el arbol o no
    public boolean contiene(K claveABuscar) {
      //llamamos al metodo buscar y comapramos si lo 
      // que devuelve es diferente de null  
     return this.buscar(claveABuscar)!=null;
    } 
    @Override
    // este metodo devuelve la cantidad de nodos en el arbol
    public int size() {
      // llamamos al metodo privado y retornamos su valor  
     return size(this.raiz); 
    }
    private int size(NodoMvias<K,V> nodoActual){
     int cantidadDeNodos=0; //creamos un contador de nodos
     if(NodoMvias.esNodoVacio(nodoActual)){
       return cantidadDeNodos;  
     } 
     if(nodoActual.esHoja()){
        cantidadDeNodos++; 
       return cantidadDeNodos;  
     }
      for(int i=0;i<orden;i++){
        cantidadDeNodos+=size(nodoActual.getHijo(i));
      }//fin del for
      return cantidadDeNodos+1;
    }
    
    @Override
      // este metodo calcula la altura que tiene el arbol
    public int altura() {
     // llamamos a ub metodo protegido   
     return this.altura(this.raiz);  
    }
    // este metodo protegido devuelve la altura de un nodo
    protected  int altura(NodoMvias<K,V> nodoActual){
     // verificamos si el nodo es vacio   
     if(NodoMvias.esNodoVacio(nodoActual)){
       return 0;  
     }  
     // verificamos si el nodo es hoja
     if(nodoActual.esHoja()){
       return 1;  
     }
     int alturaMayor=0;//guarda la altura mayor que pille
     // hacemos un for para recorrer el arbol
     for(int i=0;i<this.orden;i++){   
      // guardamos el resultado de la llamada recursiva   
      int alturaDeTurno=this.altura(nodoActual.getHijo(i));
      // verificamos si la alturaDeTurno es mayor a la alturaMayor
      if(alturaDeTurno>alturaMayor){
        // actualizamos la alturaMayor si encontro una nueva altura   
        alturaMayor=alturaDeTurno;    
      }  
     }//fin del for
     // retornamos la alturaMayor mas 1 contando a la raiz
     return alturaMayor+1;
    }
    @Override
    //este metodo limpia el arbol dejandolo vacio
    public void vaciar() {
     this.raiz = NodoMvias.nodoVacio();
    }

    @Override
     //este metodo verifica si el arbol esta vacio o no
    public boolean esArbolVacio() {
     return NodoMvias.esNodoVacio(this.raiz);
    }

    @Override
    // este metodo calcula el nivel que tiene el arbol
    public int nivel() {    
     //lmamos a un metodo privado   
     return nivel(this.raiz);// retornamos el resultado del metodo que llamo
    }
    // metodo privado que devuelve el nivel de un nodo
    private int nivel(NodoMvias<K,V> nodoActual){
      //verifica si el nodoActual es un nodoVacio  
      if(NodoMvias.esNodoVacio(nodoActual)){
        return -1;  
      }  
      //verifica si el nodoActual es una hoja
      if(nodoActual.esHoja()){
        return 0;  
      }
      
      int nivelMayor=-1;
      // hacemos un for para recorrer el arbol
       for(int i=0;i<this.orden;i++){  
         // guardamos el resultado de la llamada recursiva  
         int nivelDeTurno=this.nivel(nodoActual.getHijo(i));
         // verificamos si el nivelDeTurno es mayor al nivelMayor
         if(nivelDeTurno>nivelMayor){
           // actualizamos el nivelMayor si encontro un nuevo nivel  
            nivelMayor=nivelDeTurno; 
         }
       }//fin del for
       // retornamos el nivel ayor+1 contando a la raiz
       return nivelMayor+1;
    }

    @Override
    // este metodo realiza un recorrido a profundidad del arbol y retorna una
    // lista con todas las claves que visito
    public List<K> recorridoEnInOrden() {
     // lista para almacenar las claves visitadas durante el recorrido   
     List<K> recorrido=new ArrayList<>();
     //llamamos aun metodo privado que pondra las claves 
     this.recorridoEnInOrden(this.raiz,recorrido);
     // retrnamos la lista con los valores 
     return recorrido;
    }
    // este metodo privado hace un recorrido en inOrden y las pone en la lista 
    private void recorridoEnInOrden(NodoMvias<K,V> nodoActual,
                                                             List<K> recorrido){
    // revisamos si el nodoActual no es un nodoVacio    
    if(NodoMvias.esNodoVacio(nodoActual)){
      return;  
    }
      // hacemos un for para ir recorriendo cada nodo
       for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
          // hacemos una llamada recursiva  
          this.recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
          //y guardamos en la lista las claves que tiene el nodoActual
          recorrido.add(nodoActual.getClave(i));
       }//fin del for
       //saliendo del for hacemos una llamada recursiva 
       this.recorridoEnInOrden
               (nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()),recorrido);
     }     
    
    @Override
    // este metodo realiza un recorrido a profundidad del arbol y retorna una
    // lista con todas las claves que visito
    public List<K> recorridoEnPreOrden() {
     // lista para almacenar las claves visitadas durante el recorrido   
     List<K> recorrido = new ArrayList<>();
     //llamamos aun metodo privado  
     this.recorridoEnPreOrden(this.raiz,recorrido);
     // retornamos la ista modificada
     return recorrido;
    }
    
    // este metodo privado hace un recorrido en preOrden y las pone en la lista
    private void recorridoEnPreOrden(NodoMvias<K,V> nodoActual,
                                                             List<K> recorrido){
     // revisamos si el nodoActual no es un nodoVacio   
     if(NodoMvias.esNodoVacio(nodoActual)){
        return;  
     }    
     // hacemos un for para ir recorriendo cada nodo
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
       //guardamos en la lista las claves que tiene el nodoActual  
       recorrido.add(nodoActual.getClave(i));
       //y hacemos una llamada recursiva
       this.recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
     }//fin del for
     //saliendo del for hacemos una llamada recursiva
     this.recorridoEnPreOrden
              (nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), recorrido);
    }

    @Override
    // este metodo realiza un recorrido a profundidad del arbol y retorna una
    // lista con todas las claves que visito
    public List<K> recorridoEnPostOrden() {
     // lista para almacenar las claves visitadas durante el recorrido   
     List<K> recorrido=new ArrayList<>();
     // llamamos aun metodo privado
     this.recorridoEnPostOrden(this.raiz,recorrido);
     //retornamos la lista ya modificada
     return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoMvias<K,V> nodoActual,
                                                             List<K> recorrido){
     // revisamos si el nodoActual no es un nodoVacio
     if(NodoMvias.esNodoVacio(nodoActual)){
        return;  
     }
     //hacemos una llamada recursiva  
     this.recorridoEnPostOrden(nodoActual.getHijo(0),recorrido);
     // hacemos un for para ir recorriendo cada nodo
     for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
      // volvemos a hacer otra llamada recursiva   
      this.recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
      // y guardamos en la lista las claves que tiene el nodoActual
      recorrido.add(nodoActual.getClave(i));
     }// fin del for
    }
    @Override
         //este metodo realiza un recorrido por amplitud de todo el arbol
    // y devuelve una lista con las claves que visito
    public List<K> recorridoPorNiveles() {
     // lista para almacenar las claves visitadas durante el recorrido    
     List<K> recorrido= new ArrayList<>();
     // creamos una cola para almacenar los nodos del arbol
     Queue<NodoMvias<K,V>> colaDeNodos=new LinkedList<>();
     // insertamos la raíz del árbol en la cola para comenzar el recorrido
     colaDeNodos.offer(this.raiz); //metimos el primer nodo
     do{
     // sacamos el nodo de la cola     
     NodoMvias<K,V> nodoActual=colaDeNodos.poll();
      for(int i=0;i<nodoActual.nroDeClavesNoVacias();i++){
       K claveDelNodoActual= nodoActual.getClave(i);
       // agregamos la clave del nodoActual a la lista de recorrido
       recorrido.add(claveDelNodoActual);// metemos la clave a la lista
       // verificamos si el nodoActual tiene hijo 
       if(!nodoActual.esHijoVacio(i)){
        // si lo tiene lo metemos a la cola  
        colaDeNodos.offer(nodoActual.getHijo(i));
       }
     }//fin del for
      // verificamos si el ultimo hijo no es vacio
      if(!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())){
        // si lo tiene o metemos a la cola
        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
      } 
     }while(!colaDeNodos.isEmpty());// itrea hasta que la cola este vacia
     return recorrido; // retorna la lista 
    }
    //NOTA: todos los recorridos a excepcion del por nivels se pueden hacer 
     // recursivo
    public int cantidadDeHijosNoVaciosHastaUnNivel(int nivelHastaProcesar){
     if(this.esArbolVacio()){
       return 0;  
     }   
     return this.cantidadDeHijosNoVaciosHastaUnNivel(this.raiz,0,
                                                            nivelHastaProcesar);
    }
    private int cantidadDeHijosNoVaciosHastaUnNivel(NodoMvias<K,V> nodoActual,
                                        int nivelActual,int nivelHastaProcesar){
      if(nivelActual<nivelHastaProcesar){
         if(NodoMvias.esNodoVacio(nodoActual)|| nodoActual.esHoja()){
           return 0;  
         }
         int cantidadDeHijosNoVacios=0;
         for(int i=0;i<this.orden;i++){
           if(!nodoActual.esHijoVacio(i)){
             cantidadDeHijosNoVacios++;  
           }  
           cantidadDeHijosNoVacios+=this.cantidadDeHijosNoVaciosHastaUnNivel
           (nodoActual.getHijo(i),nivelActual+1,nivelHastaProcesar);
         }
         return cantidadDeHijosNoVacios;
      }
      return 0;
    }
}