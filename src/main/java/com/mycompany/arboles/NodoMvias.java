/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arboles;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */
public class NodoMvias<K,V> {
 private List<K> listaDeClaves;
 private List<V> listaDeValores;
 private List<NodoMvias<K,V>>listaDeHijos;
 
 public static NodoMvias nodoVacio(){
  return null;   
 }
 public static Object datoVacio(){
  return null;   
 }
 public static boolean esNodoVacio(NodoMvias nodo){
  return nodo==NodoMvias.nodoVacio();
 }
  public NodoMvias(int orden){
   this.listaDeClaves=new ArrayList<>();
   this.listaDeValores=new ArrayList<>();
   this.listaDeHijos=new ArrayList<>();
   for(int i=0; i<(orden-1);i++){
    this.listaDeClaves.add((K) NodoMvias.datoVacio());
    this.listaDeValores.add((V) NodoMvias.datoVacio());
    this.listaDeHijos.add(NodoMvias.nodoVacio());
   }
   this.listaDeHijos.add(NodoMvias.nodoVacio());
  }
  public NodoMvias(int orden,K primeraClave,V primerValor){
   this(orden);// esto llama al contructor de arriba
   this.listaDeClaves.set(0,primeraClave);
   this.listaDeValores.set(0,primerValor);
  }
  public K getClave(int posicion) {
   return listaDeClaves.get(posicion);
  }
  public void setClave(int posicion, K unaClave) {
    this.listaDeClaves.set(posicion,unaClave);
  }
  public V getValor(int posicion) {
  return listaDeValores.get(posicion);
  }
 public void setValor(int posicion,V unValor) {
   this.listaDeValores.set(posicion, unValor);
 }
 public NodoMvias<K, V> getHijo(int posicion) {
  return listaDeHijos.get(posicion);
 }
 public void setHijo(int posicion,NodoMvias<K, V> nodo) {
   this.listaDeHijos.set(posicion, nodo);
 }
 public boolean esHijoVacio(int posicion){
  return NodoMvias.esNodoVacio(this.getHijo(posicion));
 } 
 public boolean esDatoVacio(int posicion){
  return this.getValor(posicion)==NodoMvias.datoVacio();
 }
 public boolean esHoja(){
  for(int i=0;i<this.listaDeHijos.size();i++){
   if(!NodoMvias.esNodoVacio(this.getHijo(i))){
     return false;  
   }   
  }
  return true;
 }
 public int nroDeClavesNoVacias(){
  int cantidad=0;
  for(int i=0;i<this.listaDeClaves.size();i++){
    if(!this.esDatoVacio(i)){
      cantidad++;  
    }  
  }
  return cantidad;
 }
 public boolean hayClavesNoVacias(){
  return this.nroDeClavesNoVacias()!=0;
 }
 public boolean clavesLlenas(){
  return this.nroDeClavesNoVacias()==this.listaDeClaves.size();
 }
}