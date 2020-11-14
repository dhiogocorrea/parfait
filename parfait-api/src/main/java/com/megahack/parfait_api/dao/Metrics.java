package com.megahack.parfait_api.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "metrics")
@SequenceGenerator(name = "METRICS_SEQ", sequenceName = "METRICS_SEQ", initialValue = 1, allocationSize = 1)
public class Metrics {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "METRICS_SEQ")
	private long metricsId;
	
	//todo: metricas
	
	public Metrics() {}
}
