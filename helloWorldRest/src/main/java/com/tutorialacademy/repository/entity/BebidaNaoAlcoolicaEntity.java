package com.tutorialacademy.repository.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.tutorialacademy.repository.entity.enumerator.TipoBebida;

@Entity
@DiscriminatorValue(value = "1")
public class BebidaNaoAlcoolicaEntity extends BebidaEntity {
	public BebidaNaoAlcoolicaEntity() {
		super.setTipoBebida(TipoBebida.NAO_ALCOOLICA);
	}

	public BebidaNaoAlcoolicaEntity(Long bebida) {
		super(bebida);
	}

}
