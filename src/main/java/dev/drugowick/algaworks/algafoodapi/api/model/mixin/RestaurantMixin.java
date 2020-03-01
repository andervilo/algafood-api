package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.drugowick.algaworks.algafoodapi.domain.model.Address;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Cuisine cuisine;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private LocalDateTime createdDate;

    @JsonIgnore
    private LocalDateTime updatedDate;

    @JsonIgnore
    private List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}