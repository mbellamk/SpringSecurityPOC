package com.wex.poc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wex.poc.form.Page;
import com.wex.poc.form.VPayable;
import com.wex.poc.model.RandomTable;
import com.wex.poc.repository.RandomRepository;
import com.wex.poc.service.LandingPageService;

@Service("landingPageService")
public class LandingPageServiceImpl implements LandingPageService {

	@Autowired
	private RandomRepository randomRepository;

	@Autowired
	private VPayableWorkBook vPayableWorkBook;

	// private Paginator paginator;

	public List<VPayable> getVPayables(String username) {
		List<VPayable> vPayables = new ArrayList<VPayable>();
		if (username.equals("user1")) {
			VPayable vPayable = new VPayable();
			vPayable.setAmount(100);
			vPayable.setFrom("03/16/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(100);
			vPayable.setFrom("03/16/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(100);
			vPayable.setFrom("03/16/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);
		} else if (username.equals("user2")) {
			VPayable vPayable = new VPayable();
			vPayable.setAmount(200);
			vPayable.setFrom("03/17/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(200);
			vPayable.setFrom("03/17/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(200);
			vPayable.setFrom("03/17/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);
		} else if (username.equals("admin")) {
			VPayable vPayable = new VPayable();
			vPayable.setAmount(300);
			vPayable.setFrom("03/18/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(300);
			vPayable.setFrom("03/18/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);

			vPayable = new VPayable();
			vPayable.setAmount(300);
			vPayable.setFrom("03/18/2016");
			vPayable.setTo("03/20/2016");
			vPayables.add(vPayable);
		}

		return vPayables;
	}

	/*
	 * public Map<String, Object> getRandomRecords(Integer firstResult, Integer
	 * maxResults) { long total = randomRepository.countRandoms(); Page page =
	 * paginator.getPage(firstResult, maxResults, total); return buildListModel(
	 * randomRepository.getRandoms(page.getFirstResult(), page.getMaxResults()),
	 * page); }
	 */

	private Map<String, Object> buildListModel(List<Random> randoms, Page page) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("randoms", randoms);
		model.put("count", randoms.size());
		model.put("total", page.getTotal());
		model.put("firstResult", page.getFirstResult());
		model.put("maxResults", page.getMaxResults());
		model.put("nextResult", page.getNextResult());
		model.put("prevResult", page.getPrevResult());
		model.put("startResult", page.getStartResult());
		model.put("lastResult", page.getLastResult());
		return model;
	}

	protected Random getRandom(int randomId) throws NotFoundException {
		Random random = randomRepository.getRandom(randomId);
		if (random == null) {
			throw new NotFoundException(null);
		}
		return random;
	}

	protected void saveRandom(Random random) {
		randomRepository.saveRandom(random);
	}

	protected void deleteRandom(int randomId) {
		randomRepository.deleteRandom(randomId);
	}

	public List<RandomTable> testHibernateSearch(String searchText) {
		return randomRepository.getRandomList(searchText);
	}

	public void indexValues() {
		// TODO Auto-generated method stub
		randomRepository.doIndex();
	}

	public List<RandomTable> testHibernate(String searchText) {
		// TODO Auto-generated method stub
		return randomRepository.getRandomListThroughHibernate(searchText);
	}

	public XSSFWorkbook getExcel(String fileName) {
		OpenWorkBook openWorkBook = new OpenWorkBook();
		return openWorkBook.openWorkBook(fileName);
	}

	public XSSFWorkbook getUploadedExcel(MultipartFile file) {
		return vPayableWorkBook.vpayableWorkBookMultipart(file);
	}

	/*
	 * protected Map<String, Object> searchRandomsByTitle(String title, Integer
	 * firstResult, Integer maxResults) { long total =
	 * randomRepository.countRandomsByTitle(title); Page page =
	 * paginator.getPage(firstResult, maxResults, total); return buildListModel(
	 * randomRepository.searchRandomsByTitle(title, page.getFirstResult(),
	 * page.getMaxResults()), page); }
	 * 
	 * protected Map<String, Object> searchRandomsByTitlePrefix(String title,
	 * Integer firstResult, Integer maxResults) { long total =
	 * randomRepository.countRandomsByTitlePrefix(title); Page page =
	 * paginator.getPage(firstResult, maxResults, total); return buildListModel(
	 * randomRepository.searchRandomsByTitlePrefix(title, page.getFirstResult(),
	 * page.getMaxResults()), page); }
	 */
}
