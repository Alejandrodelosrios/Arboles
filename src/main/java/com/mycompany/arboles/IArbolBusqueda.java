/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arboles;

import java.util.List;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
 public void insertar(K claveAInsertar,V valorAInsertar);
 public V eliminar(K claveAEliminar);
 public V buscar(K claveABuscar);
 public boolean contiene(K claveABuscar);
 public int size();
 public int altura();
 public void vaciar();
 public boolean esArbolVacio();
 public int nivel();
 List<K> recorridoEnInOrden();
 List<K> recorridoEnPreOrden();
 List<K> recorridoEnPostOrden();
 List<K> recorridoPorNiveles();
}
