package simApp;


class OrigDestData{
	
	public PTIntersection origin;
	public PTIntersection dest;
	
	public OrigDestData( PTIntersection origin, PTIntersection dest ){
		
		this.origin = origin;
		this.dest = dest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrigDestData other = (OrigDestData) obj;
		if (dest != other.dest)
			return false;
		if (origin != other.origin)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrigDestData [origin=" + origin + ", dest=" + dest + "]";
	}

}