package com.openclassrooms.p6.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.payload.response.SingleThemeSubscriptionResponse;

/**
 * Interface responsible for mapping between {@link Subscriptions} domain models
 * and
 * {@link SingleThemeSubscriptionResponse} data transfer objects.
 */
@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mappings({
            @Mapping(target = "themeId", source = "id"),
            @Mapping(target = "isSubscribed", source = "isSubscribed")
    })
    Iterable<SingleThemeSubscriptionResponse> toDtoSubscriptions(Iterable<Subscriptions> subscriptions);
}