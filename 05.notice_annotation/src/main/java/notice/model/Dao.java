package notice.model;

import java.util.ArrayList;

public interface Dao {
	public int noticeInsert(Notice notice);
	public int noticeUpdate(Notice notice);
	public ArrayList<Notice> noticeList();
	public Notice noticeView(int num);
	public int noticeDelete(int num);
}
