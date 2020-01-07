package net.dark_roleplay.tertiary_interactor;

import net.dark_roleplay.IModule;
import net.dark_roleplay.tertiary_interactor.listeners.PlayerClientTickListener;
import net.dark_roleplay.tertiary_interactor.listeners.PlayerServerTickListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class TertiaryInteractionModule implements IModule{

    @Override
    public void registerPackets(){
//        NetworkHelper.registerPacket(TertiaryInteractionStarted.class);
//        NetworkHelper.registerPacket(TertiaryInteractionStopped.class);
    }

    @Override
    public void registerEvents(){
        if(EffectiveSide.get() == LogicalSide.CLIENT){
            MinecraftForge.EVENT_BUS.addListener(PlayerClientTickListener::tickClient);
        }else{
            MinecraftForge.EVENT_BUS.addListener(PlayerServerTickListener::tickServer);
        }
    }
}
