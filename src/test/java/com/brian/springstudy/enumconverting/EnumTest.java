package com.brian.springstudy.enumconverting;

public class EnumTest {

    public enum TestEnum {
        YEAR("Y"),
        MONTH("M"),
        DAY("D");

        private String code;

        public String getCode() {
            return code;
        }

        TestEnum(String code) {
            this.code = code;
        }
    }


    public static void main(String[] args) {
        TestEnum year = TestEnum.YEAR;
        System.out.println(year);
        System.out.println(year.name());
        System.out.println(year.ordinal());

        System.out.println("===========================");

        TestEnum month = TestEnum.valueOf("MONTH");
        System.out.println(month);

        System.out.println("===========================");

        TestEnum month2 = Enum.valueOf(TestEnum.class, "MONTH");
        System.out.println(month2);

        System.out.println("===========================");

        for (TestEnum e : TestEnum.class.getEnumConstants()) {
            System.out.println(e.getCode());
        }

        System.out.println("===========================");

        for (TestEnum e : TestEnum.values()) {
            System.out.println(e.getCode());
        }
    }
}
