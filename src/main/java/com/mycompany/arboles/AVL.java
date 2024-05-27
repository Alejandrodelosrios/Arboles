/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */
public class AVL <K extends Comparable<K>,V> extends ArbolBinarioBusqueda<K,V> {
  //constantes para verificar si esta balanaceado el arbol  
  public static final byte RANGO_SUPERIOR =1;
  public static final byte RANGO_INFERIOR =-1;
   
    //constructor
    public AVL() {
    
    }
    //constructor que se reconstruye con 4 listas 
    public AVL(List<K> listaDeClavesInOrden,List<K> 
                    listaDeClavesNoInOrden,List<V> listaDeValoresInOrden,List<V> 
                    listaDeValoresNoInOrden, boolean conPostOrden){
     super(listaDeClavesInOrden,listaDeClavesNoInOrden,listaDeValoresInOrden, 
                    listaDeValoresNoInOrden,conPostOrden);
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
     //llamamos aun metodo privado
     super.raiz=this.insertar(super.raiz,claveAInsertar,valorAInsertar);
    }
    
    // metodo privado insetar un nuevo nodo en el nodo que le pasemos
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual,K 
                                              claveAInsertar,V  valorAInsertar){
    //caso base:  verificamos si el nodoActual esta vacio y creamos un 
     //nuevo nodo    
    if(NodoBinario.esNodoVacio(nodoActual)){   
     NodoBinario<K,V> nuevoNodo= new NodoBinario(claveAInsertar,valorAInsertar);
     return nuevoNodo;
    }
    K claveDelNodoActual=nodoActual.getClave();
    //comparamos la claveAInsertar si es mayor que  claveDelNodoActual 
    if(claveAInsertar.compareTo(claveDelNodoActual)>0){
      //insertamos recursivamente en el sub arbol derecho  
      NodoBinario<K,V> supuestoNuevoHijoDerecho = 
        insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
    //actualiza el hijo derecho del nodoActual con el supuestoNuevoHijoDerecho
      nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
     //revisamos si esta balanceado el nodoActual despues de la inserccion
     //y lo retornamos
     return balancear(nodoActual);
    }
     //comparamos si la claveAInsertar es menor a la claveDelNodoActual
    if(claveAInsertar.compareTo(claveDelNodoActual)<0){
      // insertamos recursivamente en el sub arbol izquierdo  
      NodoBinario<K,V> supuestoNuevoHijoIzquierdo=
        insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
      //actualiza el hijo izquierdo del nodoActual con el supuestoHijoIzquierdo
      nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
      // revisamos si esta balanceado el nodoActual despues de la 
      //inserccion y lo retornamos
      return balancear(nodoActual);
    }else{
     //actualiza el valor del nodoActual  
     nodoActual.setValor(valorAInsertar);
     return nodoActual;
    }
  }
  // metodo auxiliar para balanacearun nodo del arbol  
  private NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual){
   //calculamos las alturas de los sub arboles izquierdo y derecho   
   int alturaPorIzquierda=super.altura(nodoActual.getHijoIzquierdo());
   int alturaPorDerecha=super.altura(nodoActual.getHijoDerecho());
   //calculamos la diferencia de alturas
   int diferenciaDeAltura= alturaPorIzquierda-alturaPorDerecha;
   // verificamos si la diferencia es mayor al RANGO_SUPERIOR 
   if(diferenciaDeAltura>AVL.RANGO_SUPERIOR){
     //bajamos al hijo del problema  
     NodoBinario<K,V> hijoIzquierdoDelNodoActual=nodoActual.getHijoIzquierdo();
     //y volvemos a calcular las alturas de los sub arboles del hijo
     alturaPorIzquierda=
                    super.altura(hijoIzquierdoDelNodoActual.getHijoIzquierdo());
     alturaPorDerecha=super.altura(hijoIzquierdoDelNodoActual.getHijoDerecho());
     // verificamos si la altura de la derecha es mayor a la de la izquierda
     if(alturaPorDerecha>alturaPorIzquierda){
       //llamamos aun metodo auxiliar para hacer una rotacion doble a la derecha  
       return rotacionDobleALaDerecha(nodoActual);  
     }
    //caso contrario llamamos aun metodo para hacer rotacion simple a la derecha 
      return rotacionSimpleALaDerecha(nodoActual);
   }
   //verificamos si la diferencia es menor al RANGO_INFERIOR
   if(diferenciaDeAltura<AVL.RANGO_INFERIOR){
     //bajamos al hijo del problema  
     NodoBinario<K,V> hijoDerechoDelNodoActual=nodoActual.getHijoDerecho();
     //y volvemos a calcular las alturas de los sub arboles del hijo
     alturaPorIzquierda=
             super.altura(hijoDerechoDelNodoActual.getHijoIzquierdo());
     alturaPorDerecha=super.altura(hijoDerechoDelNodoActual.getHijoDerecho());
     // verificamos si la altura de la izquierda es mayor a la de la derecha
     if(alturaPorIzquierda>alturaPorDerecha){
        //llamamos aun metodo auxiliar para hacer rotacion doble a la izquierda 
        return rotacionDobleALaIzquierda(nodoActual); 
     }
     //caso contrario llamamos aun metodo auxiliar para hacer rotacion 
     //simple a la izquierda
      return rotacionSimpleALaIzquierda(nodoActual);
    }
    //si la diferencia es igual a los rangos permitidos retornamos el nodoActual
    return nodoActual;
  }
  
  // este metodo auxiliar ayuda para hacer rotaaciones simples a la derecha
  private NodoBinario<K,V> rotacionSimpleALaDerecha
                                                  (NodoBinario<K,V> nodoActual){                                                  
    //guardamos el nodo que va rotar                                                  
    NodoBinario<K,V> nodoQueRota=nodoActual.getHijoIzquierdo();
    //asignamos el nuevo hijo izquierdo al nodoActual
    nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
    //y asignamos el nuevo hijo derecho al nodoQueRota
    nodoQueRota.setHijoDerecho(nodoActual);
    //retornamos el nuevo nodo ya rotado
    return nodoQueRota;
    }
  
  // este metodo auxiliar ayuda para hacer rotaciones simples a la izquierda                                                
  private NodoBinario<K,V> rotacionSimpleALaIzquierda
                                                  (NodoBinario<K,V> nodoActual){
    //guardamos el nodoque va a rotar                                                  
   NodoBinario<K,V> nodoQueRota=nodoActual.getHijoDerecho();
   // asignamos el nuevo hijo derecho al nodoActual
   nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
   //asignamos el nuevo hijo izquierdo al nodoQueRota
   nodoQueRota.setHijoIzquierdo(nodoActual);
   // retornamos el nuevo nodo ya rotado
   return nodoQueRota;
  }
  // este metodo auxiliar ayuda para hacer rotaciones dobles a la izquierda                                                
  private NodoBinario<K,V> rotacionDobleALaIzquierda(NodoBinario<K,V> 
                                                                    nodoActual){
   //llamamos al metodo rotacion simple a la derecha y le pasamos su hijo 
   // izquierdo del nodoActual y lo guardamos en nodoQueRota
   NodoBinario<K,V> nodoQueRota=
                   this.rotacionSimpleALaDerecha(nodoActual.getHijoDerecho());
   // actualizamos el nuevo hijoIzquierdo del nodoActual con el nodoQuRota
   nodoActual.setHijoDerecho(nodoQueRota);
   // llamamos al metodo rotacion simple a la izquierda y le pasamos el 
   //nodoActual  
   return this.rotacionSimpleALaIzquierda(nodoActual);
  }
  // este metodo auxiliaar ayuda para hacer rotaciones dobles a la derecha
  private NodoBinario<K,V> rotacionDobleALaDerecha(NodoBinario<K,V> nodoActual){
   //llamamos al metodo rotacion simple a la  y le pasamos su hijo 
   // izquierdo del nodoActual y lo guardamos en nodoQueRota   
   NodoBinario<K,V> nodoQueRota=
                 this.rotacionSimpleALaIzquierda(nodoActual.getHijoIzquierdo());
   nodoActual.setHijoIzquierdo(nodoQueRota);
   return this.rotacionSimpleALaDerecha(nodoActual);
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
    @Override
    protected NodoBinario<K,V> eliminar(NodoBinario<K,V>nodoActual,K
                                                                claveAEliminar){    
     K claveDelNodoActual=nodoActual.getClave();
     // si la claveAEliminar es mayor a la claveDelNodoActual
     if(claveAEliminar.compareTo(claveDelNodoActual)>0){
        //elimina el nodo del subArbol derecho  
        NodoBinario<K,V> supuestoNuevoHijoDerecho=
                      this.eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        return balancear(nodoActual);
     }
     // si la claveAEliminar es menor a la claveDelNodoActual
     if(claveAEliminar.compareTo(claveDelNodoActual)<0){
        //elimina el nodo del subArbol izquierdo   
        NodoBinario<K,V> supuestoNuevoHijoIzquierdo=
                    this.eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
        nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
        return balancear(nodoActual);
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
     NodoBinario<K,V> nodoSucesor=super.getSucesor(nodoActual.getHijoDerecho());
     // eliminamos el sucesor del arbol para reemplazar
     NodoBinario<K,V> supuestoNuevoHijoDerecho=
             this.eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
     // actualizamos el hijo derecho del nodoActual
     nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
      // reemplazamos los datos del nodoActual con los datos del nodoSucesor 
     nodoActual.setClave(nodoSucesor.getClave());
     nodoActual.setValor(nodoSucesor.getValor());
     // retornamos el nodoActual modificado
     return nodoActual;
    }
    // EJERCICIOS DEL INGE
    
    //tarea 1: saber si el arbol esta balanceado
    public boolean estaBalanceado(){
     // creamos una cola para almacenar los nodos del arbol
     Queue<NodoBinario<K,V>> colaDeNodos=new LinkedList<>();
     // insertamos la raíz del árbol en la cola para comenzar 
     colaDeNodos.offer(raiz);
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
     // calulamos las alturas de los subarboles del nodoActual
      int alturaPorIzquierda=super.altura(nodoActual.getHijoIzquierdo());
      int alturaPorDerecha=super.altura(nodoActual.getHijoDerecho());
      // calulamos la diferencia de las alturas de izquierda y derecha
      int diferenciaDeAlturas=alturaPorIzquierda-alturaPorDerecha;
      // verificamos si la diferencia esta fuera de rango permitido
      if(diferenciaDeAlturas>AVL.RANGO_SUPERIOR ||
         diferenciaDeAlturas<AVL.RANGO_INFERIOR){ 
         return false;  
      }
     }while(!colaDeNodos.isEmpty());// itrea hasta que la cola este vacia
     return true; 
    }   
  //tarea 2: saber si apartir de un nivel esta balanceado
    public boolean estaBalanceadoApartirDeUnNivel(int nivelAProcesar){
      if(nivelAProcesar>=0 && nivelAProcesar<=super.nivel()){
         return estaBalanceadoApartirDeUnNivel(this.raiz,0,nivelAProcesar); 
      }
      return false;
    }
    private boolean estaBalanceadoApartirDeUnNivel(NodoBinario<K,V> nodoActual,
                      int nivelActual,int nivelAProcesar){
     if(nivelActual>=nivelAProcesar && nivelActual<=super.nivel()){
       if(NodoBinario.esNodoVacio(nodoActual)||nodoActual.esHoja()){
         return true;  
       }
       boolean estaBalanceadoPorIzquierda=this.estaBalanceadoApartirDeUnNivel
                 (nodoActual.getHijoIzquierdo(), nivelActual+1,nivelAProcesar);
       boolean estaBalanceadoPorDerecha =this.estaBalanceadoApartirDeUnNivel
                 (nodoActual.getHijoDerecho(),nivelActual+1,nivelAProcesar);
       if(estaBalanceadoPorIzquierda && estaBalanceadoPorDerecha){
         int diferenciaDeAlturas=super.altura(nodoActual.getHijoIzquierdo())-
                                      super.altura(nodoActual.getHijoDerecho());
         if(diferenciaDeAlturas>=AVL.RANGO_INFERIOR || 
                           diferenciaDeAlturas<=AVL.RANGO_SUPERIOR){
            return true;
         }
       }
         return false;
     }
     boolean estaBalanceadoPorIzquierda=this.estaBalanceadoApartirDeUnNivel
                  (nodoActual.getHijoIzquierdo(),nivelActual+1,nivelAProcesar);
     boolean estaBalanceadoPorDerecha =this.estaBalanceadoApartirDeUnNivel
                 (nodoActual.getHijoDerecho(),nivelActual+1,nivelAProcesar);
    return estaBalanceadoPorIzquierda&&estaBalanceadoPorDerecha;
   }
}