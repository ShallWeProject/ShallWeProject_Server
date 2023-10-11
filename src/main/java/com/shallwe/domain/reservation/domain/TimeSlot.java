
package com.shallwe.domain.reservation.domain;

public enum TimeSlot {

    EIGHT("8시"),
    NINE("9시"),
    TEN("10시"),
    ELEVEN("11시"),
    TWELVE("12시"),
    THIRTEEN("13시"),
    FOURTEEN("14시"),
    FIFTEEN("15시"),
    SIXTEEN("16시"),
    SEVENTEEN("17시"),
    EIGHTEEN("18시"),
    NINETEEN("19시"),
    TWENTY("20시"),
    TWENTY_ONE("21시"),
    TWENTY_TWO("22시"),
    TWENTY_THREE("23시"),
    TWENTY_FOUR("24시");

    private final String time;

    TimeSlot(final String time) {
        this.time = time;
    }
}

