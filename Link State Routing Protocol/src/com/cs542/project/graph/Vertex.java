package com.cs542.project.graph;

/* This class is used to represent routers. */
public class Vertex {

	protected String vertexId;
	protected boolean up;

	public Vertex(String vertexId) {
		super();
		this.vertexId = vertexId;
	}

	public Vertex(String vertexId, boolean up) {
		super();
		this.vertexId = vertexId;
		this.up = up;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public String getvertexId() {
		return vertexId;
	}

	public void setvertexId(String vertexId) {
		this.vertexId = vertexId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (vertexId == null) {
			if (other.vertexId != null)
				return false;
		} else if (!vertexId.equals(other.vertexId))
			return false;
		return true;
	}
}
