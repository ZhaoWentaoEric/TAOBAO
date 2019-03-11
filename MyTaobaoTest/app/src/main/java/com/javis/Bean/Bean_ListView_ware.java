package com.javis.Bean;

public class Bean_ListView_ware {
   // "id", "name", "price", "sale_num", "address", "pic"
    private String id;
    private String name;
    private String price;
    private String sale_num;
    private String address;
    private String pic;


    public Bean_ListView_ware() {
    }

    public Bean_ListView_ware(String id, String name, String price, String sale_num, String address, String pic) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sale_num = sale_num;
        this.address = address;
        this.pic = pic;
    }

    public Bean_ListView_ware(Bean_ListView_ware bean_listView_ware) {
        this.id = bean_listView_ware.id;
        this.name = bean_listView_ware.name;
        this.price = bean_listView_ware.price;
        this.sale_num = bean_listView_ware.sale_num;
        this.address = bean_listView_ware.address;
        this.pic = bean_listView_ware.pic;
    }

    public void set_Bean_ListView_ware(Bean_ListView_ware bean_listView_ware) {
        this.id = bean_listView_ware.id;
        this.name = bean_listView_ware.name;
        this.price = bean_listView_ware.price;
        this.sale_num = bean_listView_ware.sale_num;
        this.address = bean_listView_ware.address;
        this.pic = bean_listView_ware.pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Bean_ListView_ware{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", sale_num='" + sale_num + '\'' +
                ", address='" + address + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
