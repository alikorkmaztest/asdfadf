package com.alikorkmaz.shoppingcart.delivery;

import com.alikorkmaz.shoppingcart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryCostCalculatorImplTest {

    private DeliveryCostCalculator deliveryCostCalculator;

    @Mock
    private Cart cart;

    @BeforeEach
    public void setUp() {
        deliveryCostCalculator = new DeliveryCostCalculatorImpl(2.99, 1, 2);
    }

    @Test
    public void should_calculate_delivery_cost() {
        //given
        when(cart.getNumberOfDistinctCategories()).thenReturn(2.0);
        when(cart.getNumberOfDifferentProducts()).thenReturn(4.0);

        //when
        double deliveryCost = deliveryCostCalculator.calculateFor(cart);

        //then
        assertThat(deliveryCost).isEqualTo(12.99);
    }
}