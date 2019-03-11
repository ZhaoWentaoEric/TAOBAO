package com.javis.Bean;

public class Bean_ListView_ware_1 {
 //   private String id;
    private String tv_name;
    private String tv_price;
    private String tv_sale_num;
  //  private String address;
    private String iv_pic;

    public Bean_ListView_ware_1() {
    }

    public Bean_ListView_ware_1(Bean_ListView_ware bean) {
        this.tv_name = bean.getName();
        this.tv_price = bean.getPrice();
        this.tv_sale_num = bean.getSale_num();
        this.iv_pic = bean.getPic();
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_price() {
        return tv_price;
    }

    public void setTv_price(String tv_price) {
        this.tv_price = tv_price;
    }

    public String getTv_sale_num() {
        return tv_sale_num;
    }

    public void setTv_sale_num(String tv_sale_num) {
        this.tv_sale_num = tv_sale_num;
    }

    public String getIv_pic() {
        return iv_pic;
    }

    public void setIv_pic(String iv_pic) {
        this.iv_pic = iv_pic;
    }

    @Override
    public String toString() {
        return "Bean_ListView_ware_1{" +
                "tv_name='" + tv_name + '\'' +
                ", tv_price='" + tv_price + '\'' +
                ", tv_sale_num='" + tv_sale_num + '\'' +
                ", iv_pic='" + iv_pic + '\'' +
                '}';
    }
}
