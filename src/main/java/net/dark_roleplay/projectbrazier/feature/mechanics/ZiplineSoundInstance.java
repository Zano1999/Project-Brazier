package net.dark_roleplay.projectbrazier.feature.mechanics;

import net.dark_roleplay.projectbrazier.feature.registrars.BrazierSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class ZiplineSoundInstance extends AbstractTickableSoundInstance {
	protected ZiplineSoundInstance(SoundSource soundSource) {
		super(BrazierSounds.ZIPLINE.get(), soundSource);
	}

	@Override
	public void tick() {

	}
}
