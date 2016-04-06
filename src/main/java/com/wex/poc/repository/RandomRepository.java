package com.wex.poc.repository;

import java.util.List;
import java.util.Random;

import com.wex.poc.model.RandomTable;

public interface RandomRepository {

	public List<RandomTable> getRandomList(String searchText);

	public Random getRandom(int randomid);

	public void deleteRandom(int randomId);

	public void saveRandom(Random book);

	public List<Random> getRandoms(int firstResult, int maxResults);

	public long countRandoms();

	public long countRandomsByTitle(String title);

	public List<Random> searchRandomsByTitle(String title, int firstResult,
			int maxResults);

	public long countRandomsByTitlePrefix(String title);

	public List<Random> searchRandomsByTitlePrefix(String title,
			int firstResult, int maxResults);

	public void doIndex();

	public List<RandomTable> getRandomListThroughHibernate(String searchText);
}
