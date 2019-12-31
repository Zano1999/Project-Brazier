package net.dark_roleplay.crafter.objects.guis;

public interface IScrollable {
    int getMaxScroll();
    int setScroll(int newScroll);
    int getScroll();

    default void scroll(double amount){
        int scroll = getScroll() - (int) amount;

        if(scroll < 0){
            scroll = 0;
        }else if(scroll > getMaxScroll()){
            scroll = getMaxScroll();
        }

        setScroll(scroll);
    }
}
