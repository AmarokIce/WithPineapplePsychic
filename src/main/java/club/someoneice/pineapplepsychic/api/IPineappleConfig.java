package club.someoneice.pineapplepsychic.api;

import club.someoneice.pineapplepsychic.config.ConfigBeanV2;

// Will move to api.
public interface IPineappleConfig {
    void init();

    default IPineappleConfig reload() {
        if (this instanceof ConfigBeanV2) {
            ((ConfigBeanV2) this).readFileAndOverrideConfig();
        }

        return this;
    }
}
