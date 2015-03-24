package uk.ac.abertay.eduapp.pojo;

import java.util.Calendar;

import org.apache.commons.net.ftp.FTPFile;

public class FTPFilePojo {
	private int type, hardLinkCount;
	private long size;
	private String rawListing, user, group, name, link, imageUrl, currentDir;
	private Calendar date;
	private boolean[] permissions[];
	private boolean folder;

	public FTPFilePojo(FTPFile file, String path, String imageDir) {
//		System.out.println("Image Dir ---------------> " + imageDir);

		if (file != null) {
			this.type = file.getType();
			this.hardLinkCount = file.getHardLinkCount();
			this.size = file.getSize();
			this.rawListing = file.getRawListing();
			this.user = file.getUser();
			this.group = file.getGroup();
			this.name = file.getName();
			this.link = file.getLink();
			this.date = file.getTimestamp();
			this.folder = this.type == FTPFile.DIRECTORY_TYPE ? true : false;
			this.currentDir = path;
		}

		if (folder) {
			this.imageUrl = "<img src=\"" + imageDir + "folder-icon.png" + "\">";
		} else {
			this.imageUrl = "<img src=\"" + imageDir + getImageUrl(name)
					+ "\">";
		}
//		System.out.println("Generated Html Image tag --------------------->"
//				+ this.imageUrl);
		// this.permissions = file.
	}

	public FTPFilePojo() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHardLinkCount() {
		return hardLinkCount;
	}

	public void setHardLinkCount(int hardLinkCount) {
		this.hardLinkCount = hardLinkCount;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getRawListing() {
		return rawListing;
	}

	public void setRawListing(String rawListing) {
		this.rawListing = rawListing;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean[][] getPermissions() {
		return permissions;
	}

	public void setPermissions(boolean[] permissions[]) {
		this.permissions = permissions;
	}

	public boolean isFolder() {
		return this.folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private String getImageUrl(String name) {
		String fileExtension = name.substring(name.lastIndexOf("."));
//		System.out.println("File extension ----------------> " + fileExtension);
		String imageName = "unknown_file-icon.png";
		if (fileExtension.equalsIgnoreCase(".txt")) {
			imageName = "txt-icon.png";
		} else if (fileExtension.equalsIgnoreCase(".pdf")) {
			imageName = "pdf-icon.png";
		} else if (fileExtension.equalsIgnoreCase(".doc")) {
			imageName = "doc-icon.png";
		} else if (fileExtension.equalsIgnoreCase(".ppt")) {
			imageName = "ppt-icon.png";
		} else if (fileExtension.equalsIgnoreCase(".back")) {
			imageName = "back-icon.png";
		} else if (fileExtension.equalsIgnoreCase("y")) {
			imageName = "";
		}
		return imageName;

	}

	public static void main(String[] arg) {
		new FTPFilePojo(null, null, "http://localhost:7070/EduAppServer");
	}

	public String getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
	}
}
