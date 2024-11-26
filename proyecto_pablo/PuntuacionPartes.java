package com.example.proyectopartepablo.clases;

import javax.persistence.*;

@Entity
@Table(name = "puntuacion_partes")
public class PuntuacionPartes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punt_partes")
    private int idPuntPartes;

    @Column(name = "puntos")
    private int puntos;

    @Column(name = "tipo_parte")
    private String tipoParte;

    public int getIdPuntPartes() {
        return idPuntPartes;
    }

    public void setIdPuntPartes(int idPuntPartes) {
        this.idPuntPartes = idPuntPartes;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getTipoParte() {
        return tipoParte;
    }

    public void setTipoParte(String tipoParte) {
        this.tipoParte = tipoParte;
    }
}
