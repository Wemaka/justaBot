package org.example.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

public class Rule34Api {
	private static final String baseUrl = "https://api.rule34.xxx/";

	//	private String rulePostsUrl;
	private UriComponentsBuilder rulePostsUrl;
	//	private String ruleCommentsUrl;
//	private String ruleTagsUrl;
	private int limit;
	private int page;
	private List<String> tags;
//	private int json = 0;
//	private int postId;
//	private int commentId;
//	private int tagsId;

	public Rule34Api(PostBuilder postBuilder) {
		this.limit = postBuilder.limit;
		this.page = postBuilder.page;
		this.tags = postBuilder.tags;

		rulePostsUrl = UriComponentsBuilder.fromUriString(baseUrl)
				.queryParam("page", "dapi")
				.queryParam("s", "post")
				.queryParam("q", "index")
				.queryParam("limit", this.limit);

		if (this.tags != null) {
			rulePostsUrl.queryParam("tags", String.join("+", this.tags));
		}
		if (this.page >= 0) {
			rulePostsUrl.queryParam("pid", this.page);
		}
	}

	public String getPostUrl() {
		return rulePostsUrl.toUriString();
	}

	private Elements getPosts() {
		Document doc;
		try {
			doc = Jsoup.connect(getPostUrl()).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Elements listImg = doc.select("posts");

		return listImg;
	}

	public Elements getAllPost() {
		return getPosts().select("post");
	}

	public int getPostCount() {
		return Integer.parseInt(getPosts().attr("count"));
	}

	public int getPostPageCount() {
		int count = getPostCount();
		int page = count / limit;

//		api rule34 restrictions
		return Math.min(page, 200000 / limit);
	}

	public Element getRandomPost() {
		Elements posts;
		if (page == -1) {
			int randomPage = (int) Math.floor(Math.random() * getPostPageCount());
			rulePostsUrl.queryParam("pid", randomPage);
		}
		posts = getAllPost();

		if (posts.isEmpty()) {
			return null;
		}

		return posts.get((int) Math.floor(Math.random() * posts.size()));
	}

	public static class PostBuilder {
		private int limit = 100;
		private int page = -1;
		private List<String> tags = null;

		public PostBuilder() {
			super();
		}

		public PostBuilder limit(int limit) {
			this.limit = limit;
			return this;
		}

		public PostBuilder page(int page) {
			this.page = page;
			return this;
		}

		public PostBuilder tags(List<String> tags) {
			this.tags = tags;
			return this;
		}

		private boolean validatePostApi() {
			return (limit >= 0 && limit <= 100 && page >= 0);
		}

		public Rule34Api build() {
			return new Rule34Api(this);
		}
	}
}
