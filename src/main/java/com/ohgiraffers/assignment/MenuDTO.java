package com.ohgiraffers.assignment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "메뉴판 정보 DTO")
public class MenuDTO {

    @Schema(description = "메뉴번호(PK)")
    private int no;

    @Schema(description = "메뉴이름")
    private String name;

    @Schema(description = "메뉴가격")
    private int price;

    @Schema(description = "주문 일시")
    private LocalDate enrollDate;

    public MenuDTO() {
    }

    public MenuDTO(int no, String name, int price, LocalDate enrollDate) {
        this.no = no;
        this.name = name;
        this.price = price;
        this.enrollDate = enrollDate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", enrollDate=" + enrollDate +
                '}';
    }
}
