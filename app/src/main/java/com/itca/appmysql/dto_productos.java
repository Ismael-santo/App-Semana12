package com.itca.appmysql;

public class dto_productos {

    int id_pro;
    String nom_prod;
    String des_prod;
    double stock;
    double precio;
    String uni_medida;
    int estado_prod;
    int categoria;

    public  dto_productos(){

    }

    public dto_productos(int id_pro, String nom_prod, String des_prod, double stock, double precio, String uni_medida, int estado_prod, int categoria){
        this.id_pro = id_pro;
        this.nom_prod = nom_prod;
        this.des_prod = des_prod;
        this.stock = stock;
        this.precio = precio;
        this.uni_medida = uni_medida;
        this.estado_prod = estado_prod;
        this.categoria = categoria;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getNom_prod() {
        return nom_prod;
    }

    public void setNom_prod(String nom_prod) {
        this.nom_prod = nom_prod;
    }

    public String getDes_prod() {
        return des_prod;
    }

    public void setDes_prod(String des_prod) {
        this.des_prod = des_prod;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUni_medida() {
        return uni_medida;
    }

    public void setUni_medida(String uni_medida) {
        this.uni_medida = uni_medida;
    }

    public int getEstado_prod() {
        return estado_prod;
    }

    public void setEstado_prod(int estado_prod) {
        this.estado_prod = estado_prod;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
