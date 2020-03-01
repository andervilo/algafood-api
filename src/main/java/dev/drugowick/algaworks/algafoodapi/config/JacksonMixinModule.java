package dev.drugowick.algaworks.algafoodapi.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.drugowick.algaworks.algafoodapi.api.model.mixin.*;
import dev.drugowick.algaworks.algafoodapi.domain.model.*;
import org.springframework.stereotype.Component;

/**
 * This is not a Spring configuration, but an app configuration so I thought this would be a good place.
 *
 * What this class does is tell Jackson to mix (hence the "Mixin" suffix) together two classes when dealing with
 * serialization or de-serialization. Now only the *Mixin classes contain Jackson annotations.
 *
 * The purpose is to separate concerns allowing for the domain class not to deal with (de)serialization.
 */
@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(Cuisine.class, CuisineMixin.class);
        setMixInAnnotation(OrderItem.class, OrderItemMixin.class);
        setMixInAnnotation(Product.class, ProductMixin.class);
        setMixInAnnotation(User.class, UserMixin.class);
        setMixInAnnotation(City.class, CityMixin.class);
    }
}
