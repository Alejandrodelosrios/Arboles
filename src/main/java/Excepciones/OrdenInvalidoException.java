/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author EQUIPO
 */
public class OrdenInvalidoException extends Exception{

    public OrdenInvalidoException() {
     super("orden del arbol debe ser al menos 3");    
    }

    public OrdenInvalidoException(String message) {
        super(message);
    }
    
    
}
