package com.megahack.parfait_api.service;

import java.util.List;

public interface BaseService<DAO, DTO> {
	List<DAO> getAll();
	DAO get(long id);
	void create(DTO dto);
	void delete(long id);
}
