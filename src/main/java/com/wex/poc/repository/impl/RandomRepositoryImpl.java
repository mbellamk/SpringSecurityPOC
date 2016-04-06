package com.wex.poc.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.ProjectionConstants;
import org.hibernate.search.Search;
import org.hibernate.search.SearchException;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wex.poc.model.RandomTable;
import com.wex.poc.repository.RandomRepository;

@Repository("randomRepository")
@Transactional
public class RandomRepositoryImpl implements RandomRepository {

	private static final String TITLE_INDEX = "title";
	private static final String TITLE_FULLTEXT_INDEX = "title_fulltext";

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<RandomTable> getRandomList(String searchText) {
		/*
		 * try { doIndex(); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());
		QueryBuilder mythQB = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(RandomTable.class).get();
		Query luceneQuery = mythQB.keyword().onField("text")
				.matching(searchText).createQuery();
		List<RandomTable> results = (List<RandomTable>) fullTextSession
				.createFullTextQuery(luceneQuery, RandomTable.class)
				.setProjection(ProjectionConstants.THIS,
						ProjectionConstants.SCORE).list();

		return results;
	}

	public void doIndex() {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());
		try {

			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fullTextSession.close();
		}
	}

	public List<RandomTable> getRandomListThroughHibernate(String searchText) {
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(RandomTable.class);
		query.add(Restrictions.like("text", searchText, MatchMode.ANYWHERE));

		List<RandomTable> resultList = query.list();
		return resultList;
	}

	public Random getRandom(int randomid) {
		return (Random) getCurrentSession().get(Random.class, randomid);
	}

	public void deleteRandom(int randomId) {
		Random random = (Random) getCurrentSession()
				.get(Random.class, randomId);
		if (random != null) {
			getCurrentSession().delete(random);
		}
	}

	public void saveRandom(Random book) {
		getCurrentSession().saveOrUpdate(book);
	}

	@SuppressWarnings("unchecked")
	public List<Random> getRandoms(int firstResult, int maxResults) {
		return getCurrentSession().createCriteria(Random.class)
				.addOrder(Order.asc("title")).setFirstResult(firstResult)
				.setMaxResults(maxResults).list();
	}

	public long countRandoms() {
		return (Long) getCurrentSession().createCriteria(Random.class)
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	public void index() throws InterruptedException {
		getFullTextSession().createIndexer().startAndWait();
	}

	public long countRandomsByTitle(String title) {
		if (StringUtils.isEmpty(title)) {
			return 0L;
		}
		try {
			return getFullTextSession().createFullTextQuery(
					buildPhraseQuery(TITLE_INDEX, title), Random.class)
					.getResultSize();
		} catch (SearchException e) {
			return 0L;
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Random> searchRandomsByTitle(String title, int firstResult,
			int maxResults) {
		if (StringUtils.isEmpty(title)) {
			return new ArrayList<Random>();
		}
		try {
			return buildFullTextQuery(buildPhraseQuery(TITLE_INDEX, title),
					TITLE_INDEX, firstResult, maxResults).list();
		} catch (SearchException e) {
			return new ArrayList<Random>();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	private FullTextQuery buildFullTextQuery(Query query, String index,
			int firstResult, int maxResults) {
		return getFullTextSession().createFullTextQuery(query, Random.class)
				.setSort(new Sort(new SortField(index, SortField.STRING)))
				.setFirstResult(firstResult).setMaxResults(maxResults);
	}

	private Query buildPhraseQuery(String field, String value) {
		return getFullTextSession().getSearchFactory().buildQueryBuilder()
				.forEntity(Random.class).get().phrase().onField(field)
				.sentence(value).createQuery();
	}

	public long countRandomsByTitlePrefix(String title) {
		if (StringUtils.isEmpty(title)) {
			return 0L;
		}
		try {
			return getFullTextSession()
					.createFullTextQuery(
							buildPrefixQuery(TITLE_FULLTEXT_INDEX, title),
							Random.class).getResultSize();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Random> searchRandomsByTitlePrefix(String title,
			int firstResult, int maxResults) {
		if (StringUtils.isEmpty(title)) {
			return new ArrayList<Random>();
		}
		try {
			return buildFullTextQuery(
					buildPrefixQuery(TITLE_FULLTEXT_INDEX, title.toLowerCase()),
					TITLE_FULLTEXT_INDEX, firstResult, maxResults).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
	}

	private Query buildPrefixQuery(String fieldName, String value) {
		return new PrefixQuery(new Term(fieldName, value));
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	private FullTextSession getFullTextSession() {
		return Search.getFullTextSession(getCurrentSession());
	}

}