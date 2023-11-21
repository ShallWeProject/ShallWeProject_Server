package com.shallwe.domain.shopowner.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShopOwnerGiftManageRes {

  private String title;
  private String subTitle;
  private List<ShopOwnerReservationRes> reservationList;

  public ShopOwnerGiftManageRes(String title, String subTitle,
      List<ShopOwnerReservationRes> reservationList) {
    this.title = title;
    this.subTitle = subTitle;
    this.reservationList = reservationList;
  }
}
