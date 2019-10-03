package net.dark_roleplay;

public interface IModule {

    default void modInit(){}

    default void registerPackets(){}

    default void registerEvents(){}
}
