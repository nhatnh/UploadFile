package com.movie.model;

public class MovieRecord {

	private Long id;
	private String checkSum;
	private String title;
	private String director;
	private String description;
	private String producer;
	private String mainActor;
	private String sourceFile;

	public MovieRecord() {
		id = (long) 0;
	}

	public MovieRecord(Long id, String checkSum, String title, String director, String description, 
			String producer, String mainActor, String sourceFile) {
		this.id = id;
		this.checkSum = checkSum;
		this.title = title;
		this.director = director;
		this.description = description;
		this.producer = producer;
		this.mainActor = mainActor;
		this.sourceFile = sourceFile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getMainActor() {
		return mainActor;
	}

	public void setMainActor(String mainActor) {
		this.mainActor = mainActor;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MovieRecord))
			return false;
		MovieRecord other = (MovieRecord) obj;
		if (id != other.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MovieRecord [id=" + id + ", checksum=" + checkSum + ", title=" + title + ", director=" 
				+ director + ", description=" + description + ", producer=" + producer + ", mainActor=" 
				+ mainActor + ", sourceFile=" + sourceFile + "]";
	}
	
	// Support creating a new Movie Record very flexible
	public static Builder getBuilder() {
        return new Builder();
    }

	public static class Builder {
		private MovieRecord built;

		public MovieRecord build() {
			return built;
		}
		public Builder() {
			built = new MovieRecord();
		}
		public Builder id(Long id) {
			built.id = id;
			return this;
		}
		public Builder title(String title) {
			built.title = title;
			return this;
		}
		public Builder checkSum(String checkSum) {
			built.checkSum = checkSum;
			return this;
		}
		public Builder director(String director) {
			built.director = director;
			return this;
		}
		public Builder description(String description) {
			built.description = description;
			return this;
		}
		public Builder producer(String producer) {
			built.producer = producer;
			return this;
		}
		public Builder mainActor(String mainActor) {
			built.mainActor = mainActor;
			return this;
		}
		public Builder sourceFile(String sourceFile) {
			built.sourceFile = sourceFile;
			return this;
		}
	}

}
