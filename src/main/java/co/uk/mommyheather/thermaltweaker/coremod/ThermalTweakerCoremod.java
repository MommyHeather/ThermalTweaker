package co.uk.mommyheather.thermaltweaker.coremod;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class ThermalTweakerCoremod implements IFMLLoadingPlugin {
    
    @Override
    public String[] getASMTransformerClass() {
        return new String[] { ThermalTweakerTransformer.class.getName() };
    }
    
    @Override
    public String getModContainerClass() {
        return null;
    }
    
    @Override
    public String getSetupClass() {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data) {
        
    }
    
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
    
}
