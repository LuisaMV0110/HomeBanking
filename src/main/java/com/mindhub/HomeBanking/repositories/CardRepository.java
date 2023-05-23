package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.Card;
import com.mindhub.HomeBanking.models.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
    Card findByNumber(String number);
    Card findById(long id);
}
