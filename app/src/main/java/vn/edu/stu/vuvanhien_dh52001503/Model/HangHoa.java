package vn.edu.stu.vuvanhien_dh52001503.Model;

public class HangHoa {
    private int ma;
    private String ten;
    public HangHoa(Integer ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public HangHoa() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "Mã :" + ma +"\n"+
                "Tên :" + ten;
    }

}
