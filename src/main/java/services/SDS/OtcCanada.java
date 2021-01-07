package services.SDS;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class OtcCanada {
    private HashMap<String, String> records;
    private HashMap<String, OtcCandaDisclosure> disclosures;

    public void scrape() {
        records = new HashMap<>();
        disclosures = new HashMap<>();
        ArrayList<String> historyRecods = new ArrayList<String>() {
            {
                add("_wpcmWpid=&wpcmVal=&MSOWebPartPage_PostbackSource=&MSOTlPn_SelectedWpId=&MSOTlPn_View=0&MSOTlPn_ShowSettings=False&MSOGallery_SelectedLibrary=&MSOGallery_FilterString=&MSOTlPn_Button=none&__EVENTTARGET=ctl00%24ctl39%24g_bd8fd9d9_59af_4a24_b5ca_be6d9ec191f7&__EVENTARGUMENT=dvt_firstrow%3D%7B11%7D%3Bdvt_startposition%3D%7BPaged%3DTRUE%26p_ReleaseDate%3D20200807+12%3A00%3A27%26p_ID%3D24415%7D&__REQUESTDIGEST=0xD8CC264E33CC26412EF394533AC1F32F4791196FA88FDCD729A5A83838E052EB2557EC63F81BF581EF1A711D33E9875DFCFE4C6FB287C4C9AA5612DCABFE4726%2C05+Jan+2021+07%3A15%3A22+-0000&MSOSPWebPartManager_DisplayModeName=Browse&MSOSPWebPartManager_ExitingDesignMode=false&MSOWebPartPage_Shared=&MSOLayout_LayoutChanges=&MSOLayout_InDesignMode=&_wpSelected=&_wzSelected=&MSOSPWebPartManager_OldDisplayModeName=Browse&MSOSPWebPartManager_StartWebPartEditingName=false&MSOSPWebPartManager_EndWebPartEditing=false&__VIEWSTATE=%2FwEPDwUBMA9kFgJmD2QWAgIBD2QWBAIBD2QWBgIXD2QWAgIDD2QWAmYPZBYCZg88KwAGAGQCGQ9kFgICAQ9kFgYFJmdfNDM4Y2QzN2FfNTU1Ml80YzZhX2JhN2ZfYmNkY2VhNGM4NDA2D2QWBGYPFgIeB1Zpc2libGVoZAIBDxYCHwBoZAUmZ19iZDhmZDlkOV81OWFmXzRhMjRfYjVjYV9iZTZkOWVjMTkxZjcPDxYSHgtEZXNjcmlwdGlvbmUeCURpcmVjdGlvbgsqKlN5c3RlbS5XZWIuVUkuV2ViQ29udHJvbHMuQ29udGVudERpcmVjdGlvbgAeBVRpdGxlBQpEYXRhVmlldyAxHgpDaHJvbWVUeXBlAgIeBF8hU0ICgIMIHgVXaWR0aBweEEZpbHRlck9wZXJhdGlvbnMyjQUAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAQBAAAAkwJTeXN0ZW0uQ29sbGVjdGlvbnMuR2VuZXJpYy5EaWN0aW9uYXJ5YDJbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV0sW01pY3Jvc29mdC5TaGFyZVBvaW50LldlYlBhcnRQYWdlcy5GaWx0ZXJPcGVyYXRpb24sIE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWNdXQMAAAAHVmVyc2lvbghDb21wYXJlcghIYXNoU2l6ZQADAAiSAVN5c3RlbS5Db2xsZWN0aW9ucy5HZW5lcmljLkdlbmVyaWNFcXVhbGl0eUNvbXBhcmVyYDFbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV1dCAAAAAAJAgAAAAAAAAAEAgAAAJIBU3lzdGVtLkNvbGxlY3Rpb25zLkdlbmVyaWMuR2VuZXJpY0VxdWFsaXR5Q29tcGFyZXJgMVtbU3lzdGVtLlN0cmluZywgbXNjb3JsaWIsIFZlcnNpb249NC4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iNzdhNWM1NjE5MzRlMDg5XV0AAAAACx4GSGVpZ2h0HB4LUGFyYW1WYWx1ZXMy8wYAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAwCAAAAWE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWMFAQAAAD1NaWNyb3NvZnQuU2hhcmVQb2ludC5XZWJQYXJ0UGFnZXMuUGFyYW1ldGVyTmFtZVZhbHVlSGFzaHRhYmxlAQAAAAVfY29sbAMcU3lzdGVtLkNvbGxlY3Rpb25zLkhhc2h0YWJsZQIAAAAJAwAAAAQDAAAAHFN5c3RlbS5Db2xsZWN0aW9ucy5IYXNodGFibGUHAAAACkxvYWRGYWN0b3IHVmVyc2lvbghDb21wYXJlchBIYXNoQ29kZVByb3ZpZGVyCEhhc2hTaXplBEtleXMGVmFsdWVzAAADAwAFBQsIHFN5c3RlbS5Db2xsZWN0aW9ucy5JQ29tcGFyZXIkU3lzdGVtLkNvbGxlY3Rpb25zLklIYXNoQ29kZVByb3ZpZGVyCOxROD8gAAAACgolAAAACQQAAAAJBQAAABAEAAAADAAAAAYGAAAADGR2dF9maXJzdHJvdwYHAAAABkxpbmtJRAYIAAAAEWR2dF9zdGFydHBvc2l0aW9uBgkAAAANTWFudWFsUmVmcmVzaAYKAAAAC01heGltdW1Sb3dzBgsAAAAMbmV4dHBhZ2VkYXRhBgwAAAANU3RhcnRSb3dJbmRleAYNAAAAEGR2dF9uZXh0cGFnZWRhdGEGDgAAAAZMaXN0SUQGDwAAAAVUb2RheQYQAAAACkZpbHRlckxpbmsGEQAAAAZVc2VySUQQBQAAAAwAAAAGEgAAAAExBhMAAAADNzExBhQAAAAABhUAAAAFRmFsc2UGFgAAAAIxMAYXAAAAATAGGAAAAAEwBhkAAAA7UGFnZWQ9VFJVRSZwX1JlbGVhc2VEYXRlPTIwMjAwODA3JTIwMTIlM2EwMCUzYTI3JnBfSUQ9MjQ0MTUGGgAAACZ7QTM2QjYzN0UtODE1RC00RDJFLUE1NTUtQzkwQkIzOTMyMjkyfQYbAAAAFDIwMjEtMDEtMDVUMDI6MTU6MjJaBhwAAAARPyZQYWdlRmlyc3RSb3c9MSYGHQAAAA9DdXJyZW50VXNlck5hbWULZGQFJmdfNmM0YTMzMGVfYjIxNF80N2I3X2JhMjdfZjEyZDY1ZTQ5ZjE3D2QWBGYPFgIfAGhkAgEPFgIfAGhkAiMPZBYCZg9kFgJmDzwrAAYAZAIHD2QWAgIHD2QWAgICD2QWAgIFD2QWAgIDDxYCHwBoFgJmD2QWBAICD2QWBgIBDxYCHwBoZAIDDxYCHwBoZAIFDxYCHwBoZAIDDw8WAh4JQWNjZXNzS2V5BQEvZGQYAgUQY3RsMDAkQ3VycmVudE5hdg8PZAUcTWFya2V0IE1vbml0b3JpbmcgJiBBbmFseXNpc2QFGWN0bDAwJFRvcE5hdmlnYXRpb25NZW51VjQPD2QFCEluZHVzdHJ5ZEqMmLjCpVGeU6fyiCgxBuy%2FYo4uBO0qzJfOqe75LPD%2F&__VIEWSTATEGENERATOR=4E82104B&__EVENTVALIDATION=%2FwEdAAOaszfPCIDgL4tNCn%2FkkKVJI9WgT0tlKuBuDzz85Ii%2FZMojbFppxYH1m8FlZhwcrxN0BUDXJDCp9C%2BJ45sv2dSIWMMy4NiPUlSCPCX%2B%2Flk%2BTQ%3D%3D&ctl00%24g_70e4ef30_5add_4475_8351_8c8418406327%24ctl00%24txbKeyWord=");
                add("_wpcmWpid=&wpcmVal=&MSOWebPartPage_PostbackSource=&MSOTlPn_SelectedWpId=&MSOTlPn_View=0&MSOTlPn_ShowSettings=False&MSOGallery_SelectedLibrary=&MSOGallery_FilterString=&MSOTlPn_Button=none&__EVENTTARGET=ctl00%24ctl39%24g_bd8fd9d9_59af_4a24_b5ca_be6d9ec191f7&__EVENTARGUMENT=dvt_firstrow%3D%7B21%7D%3Bdvt_startposition%3D%7BPaged%3DTRUE%26p_ReleaseDate%3D20200306+13%3A00%3A27%26p_ID%3D23845%7D&__REQUESTDIGEST=0xDBE50AE3744DE5BA0B2E2436D7CDB959FEBD1DFB8720B172D848F234C3B6E17CCFD4E486A3545B936F6B03408F573D4184294C2C2EFC12D8E7CFB84A886E9FAB%2C05+Jan+2021+07%3A15%3A46+-0000&MSOSPWebPartManager_DisplayModeName=Browse&MSOSPWebPartManager_ExitingDesignMode=false&MSOWebPartPage_Shared=&MSOLayout_LayoutChanges=&MSOLayout_InDesignMode=&_wpSelected=&_wzSelected=&MSOSPWebPartManager_OldDisplayModeName=Browse&MSOSPWebPartManager_StartWebPartEditingName=false&MSOSPWebPartManager_EndWebPartEditing=false&__VIEWSTATE=%2FwEPDwUBMA9kFgJmD2QWAgIBD2QWBAIBD2QWBgIXD2QWAgIDD2QWAmYPZBYCZg88KwAGAGQCGQ9kFgICAQ9kFgYFJmdfNDM4Y2QzN2FfNTU1Ml80YzZhX2JhN2ZfYmNkY2VhNGM4NDA2D2QWBGYPFgIeB1Zpc2libGVoZAIBDxYCHwBoZAUmZ19iZDhmZDlkOV81OWFmXzRhMjRfYjVjYV9iZTZkOWVjMTkxZjcPDxYSHgtEZXNjcmlwdGlvbmUeCURpcmVjdGlvbgsqKlN5c3RlbS5XZWIuVUkuV2ViQ29udHJvbHMuQ29udGVudERpcmVjdGlvbgAeBVRpdGxlBQpEYXRhVmlldyAxHgpDaHJvbWVUeXBlAgIeBF8hU0ICgIMIHgVXaWR0aBweEEZpbHRlck9wZXJhdGlvbnMyjQUAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAQBAAAAkwJTeXN0ZW0uQ29sbGVjdGlvbnMuR2VuZXJpYy5EaWN0aW9uYXJ5YDJbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV0sW01pY3Jvc29mdC5TaGFyZVBvaW50LldlYlBhcnRQYWdlcy5GaWx0ZXJPcGVyYXRpb24sIE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWNdXQMAAAAHVmVyc2lvbghDb21wYXJlcghIYXNoU2l6ZQADAAiSAVN5c3RlbS5Db2xsZWN0aW9ucy5HZW5lcmljLkdlbmVyaWNFcXVhbGl0eUNvbXBhcmVyYDFbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV1dCAAAAAAJAgAAAAAAAAAEAgAAAJIBU3lzdGVtLkNvbGxlY3Rpb25zLkdlbmVyaWMuR2VuZXJpY0VxdWFsaXR5Q29tcGFyZXJgMVtbU3lzdGVtLlN0cmluZywgbXNjb3JsaWIsIFZlcnNpb249NC4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iNzdhNWM1NjE5MzRlMDg5XV0AAAAACx4GSGVpZ2h0HB4LUGFyYW1WYWx1ZXMy3wcAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAwCAAAAWE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWMFAQAAAD1NaWNyb3NvZnQuU2hhcmVQb2ludC5XZWJQYXJ0UGFnZXMuUGFyYW1ldGVyTmFtZVZhbHVlSGFzaHRhYmxlAQAAAAVfY29sbAMcU3lzdGVtLkNvbGxlY3Rpb25zLkhhc2h0YWJsZQIAAAAJAwAAAAQDAAAAHFN5c3RlbS5Db2xsZWN0aW9ucy5IYXNodGFibGUHAAAACkxvYWRGYWN0b3IHVmVyc2lvbghDb21wYXJlchBIYXNoQ29kZVByb3ZpZGVyCEhhc2hTaXplBEtleXMGVmFsdWVzAAADAwAFBQsIHFN5c3RlbS5Db2xsZWN0aW9ucy5JQ29tcGFyZXIkU3lzdGVtLkNvbGxlY3Rpb25zLklIYXNoQ29kZVByb3ZpZGVyCOxROD8mAAAACgolAAAACQQAAAAJBQAAABAEAAAADAAAAAYGAAAABlVzZXJJRAYHAAAABkxpbmtJRAYIAAAADU1hbnVhbFJlZnJlc2gGCQAAAAtNYXhpbXVtUm93cwYKAAAADGR2dF9maXJzdHJvdwYLAAAADG5leHRwYWdlZGF0YQYMAAAADVN0YXJ0Um93SW5kZXgGDQAAABBkdnRfbmV4dHBhZ2VkYXRhBg4AAAAGTGlzdElEBg8AAAAFVG9kYXkGEAAAAApGaWx0ZXJMaW5rBhEAAAARZHZ0X3N0YXJ0cG9zaXRpb24QBQAAAAwAAAAGEgAAAA9DdXJyZW50VXNlck5hbWUGEwAAAAM3MTEGFAAAAAVGYWxzZQYVAAAAAjEwBhYAAAACMTEGFwAAAAEwBhgAAAABMAYZAAAAO1BhZ2VkPVRSVUUmcF9SZWxlYXNlRGF0ZT0yMDIwMDMwNiUyMDEzJTNhMDAlM2EyNyZwX0lEPTIzODQ1BhoAAAAme0EzNkI2MzdFLTgxNUQtNEQyRS1BNTU1LUM5MEJCMzkzMjI5Mn0GGwAAABQyMDIxLTAxLTA1VDAyOjE1OjQ2WgYcAAAARz9QYWdlZD1UUlVFJnBfUmVsZWFzZURhdGU9MjAyMDA4MDcgMTI6MDA6MjcmcF9JRD0yNDQxNSZQYWdlRmlyc3RSb3c9MTEmBh0AAAA1UGFnZWQ9VFJVRSZwX1JlbGVhc2VEYXRlPTIwMjAwODA3IDEyOjAwOjI3JnBfSUQ9MjQ0MTULZGQFJmdfNmM0YTMzMGVfYjIxNF80N2I3X2JhMjdfZjEyZDY1ZTQ5ZjE3D2QWBGYPFgIfAGhkAgEPFgIfAGhkAiMPZBYCZg9kFgJmDzwrAAYAZAIHD2QWAgIHD2QWAgICD2QWAgIFD2QWAgIDDxYCHwBoFgJmD2QWBAICD2QWBgIBDxYCHwBoZAIDDxYCHwBoZAIFDxYCHwBoZAIDDw8WAh4JQWNjZXNzS2V5BQEvZGQYAgUQY3RsMDAkQ3VycmVudE5hdg8PZAUcTWFya2V0IE1vbml0b3JpbmcgJiBBbmFseXNpc2QFGWN0bDAwJFRvcE5hdmlnYXRpb25NZW51VjQPD2QFCEluZHVzdHJ5ZPHLyd9BfeJEPJVL1YI9UARJZD3lYYMIxhRy%2FtASvlmU&__VIEWSTATEGENERATOR=4E82104B&__EVENTVALIDATION=%2FwEdAAPZ5ROMdXKHPG1UEP9t1QcHI9WgT0tlKuBuDzz85Ii%2FZMojbFppxYH1m8FlZhwcrxPKhtxokQu3q%2Fx9DaeKNSFgu8VuyFWxmWMazm5jtNAn6g%3D%3D&ctl00%24g_70e4ef30_5add_4475_8351_8c8418406327%24ctl00%24txbKeyWord=");
                add("_wpcmWpid=&wpcmVal=&MSOWebPartPage_PostbackSource=&MSOTlPn_SelectedWpId=&MSOTlPn_View=0&MSOTlPn_ShowSettings=False&MSOGallery_SelectedLibrary=&MSOGallery_FilterString=&MSOTlPn_Button=none&__EVENTTARGET=ctl00%24ctl39%24g_bd8fd9d9_59af_4a24_b5ca_be6d9ec191f7&__EVENTARGUMENT=dvt_firstrow%3D%7B31%7D%3Bdvt_startposition%3D%7BPaged%3DTRUE%26p_ReleaseDate%3D20191004+12%3A00%3A27%26p_ID%3D23311%7D&__REQUESTDIGEST=0x677164EA524AC63068ECC9FDC18900D23C2CDCBE9BF320A29589377AD6AEB0DEB8D68E5E8CE517BF50634C1EDA537A6E6A98CC497B0E05904E42B05935E7A0F6%2C05+Jan+2021+07%3A20%3A31+-0000&MSOSPWebPartManager_DisplayModeName=Browse&MSOSPWebPartManager_ExitingDesignMode=false&MSOWebPartPage_Shared=&MSOLayout_LayoutChanges=&MSOLayout_InDesignMode=&_wpSelected=&_wzSelected=&MSOSPWebPartManager_OldDisplayModeName=Browse&MSOSPWebPartManager_StartWebPartEditingName=false&MSOSPWebPartManager_EndWebPartEditing=false&__VIEWSTATE=%2FwEPDwUBMA9kFgJmD2QWAgIBD2QWBAIBD2QWBgIXD2QWAgIDD2QWAmYPZBYCZg88KwAGAGQCGQ9kFgICAQ9kFgYFJmdfNDM4Y2QzN2FfNTU1Ml80YzZhX2JhN2ZfYmNkY2VhNGM4NDA2D2QWBGYPFgIeB1Zpc2libGVoZAIBDxYCHwBoZAUmZ19iZDhmZDlkOV81OWFmXzRhMjRfYjVjYV9iZTZkOWVjMTkxZjcPDxYSHgtEZXNjcmlwdGlvbmUeCURpcmVjdGlvbgsqKlN5c3RlbS5XZWIuVUkuV2ViQ29udHJvbHMuQ29udGVudERpcmVjdGlvbgAeBVRpdGxlBQpEYXRhVmlldyAxHgpDaHJvbWVUeXBlAgIeBF8hU0ICgIMIHgVXaWR0aBweEEZpbHRlck9wZXJhdGlvbnMyjQUAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAQBAAAAkwJTeXN0ZW0uQ29sbGVjdGlvbnMuR2VuZXJpYy5EaWN0aW9uYXJ5YDJbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV0sW01pY3Jvc29mdC5TaGFyZVBvaW50LldlYlBhcnRQYWdlcy5GaWx0ZXJPcGVyYXRpb24sIE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWNdXQMAAAAHVmVyc2lvbghDb21wYXJlcghIYXNoU2l6ZQADAAiSAVN5c3RlbS5Db2xsZWN0aW9ucy5HZW5lcmljLkdlbmVyaWNFcXVhbGl0eUNvbXBhcmVyYDFbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV1dCAAAAAAJAgAAAAAAAAAEAgAAAJIBU3lzdGVtLkNvbGxlY3Rpb25zLkdlbmVyaWMuR2VuZXJpY0VxdWFsaXR5Q29tcGFyZXJgMVtbU3lzdGVtLlN0cmluZywgbXNjb3JsaWIsIFZlcnNpb249NC4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iNzdhNWM1NjE5MzRlMDg5XV0AAAAACx4GSGVpZ2h0HB4LUGFyYW1WYWx1ZXMy3wcAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAwCAAAAWE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWMFAQAAAD1NaWNyb3NvZnQuU2hhcmVQb2ludC5XZWJQYXJ0UGFnZXMuUGFyYW1ldGVyTmFtZVZhbHVlSGFzaHRhYmxlAQAAAAVfY29sbAMcU3lzdGVtLkNvbGxlY3Rpb25zLkhhc2h0YWJsZQIAAAAJAwAAAAQDAAAAHFN5c3RlbS5Db2xsZWN0aW9ucy5IYXNodGFibGUHAAAACkxvYWRGYWN0b3IHVmVyc2lvbghDb21wYXJlchBIYXNoQ29kZVByb3ZpZGVyCEhhc2hTaXplBEtleXMGVmFsdWVzAAADAwAFBQsIHFN5c3RlbS5Db2xsZWN0aW9ucy5JQ29tcGFyZXIkU3lzdGVtLkNvbGxlY3Rpb25zLklIYXNoQ29kZVByb3ZpZGVyCOxROD8sAAAACgolAAAACQQAAAAJBQAAABAEAAAADAAAAAYGAAAADGR2dF9maXJzdHJvdwYHAAAABkxpbmtJRAYIAAAAEWR2dF9zdGFydHBvc2l0aW9uBgkAAAANTWFudWFsUmVmcmVzaAYKAAAAC01heGltdW1Sb3dzBgsAAAAMbmV4dHBhZ2VkYXRhBgwAAAANU3RhcnRSb3dJbmRleAYNAAAAEGR2dF9uZXh0cGFnZWRhdGEGDgAAAAZMaXN0SUQGDwAAAAVUb2RheQYQAAAACkZpbHRlckxpbmsGEQAAAAZVc2VySUQQBQAAAAwAAAAGEgAAAAIyMQYTAAAAAzcxMQYUAAAANVBhZ2VkPVRSVUUmcF9SZWxlYXNlRGF0ZT0yMDIwMDMwNiAxMzowMDoyNyZwX0lEPTIzODQ1BhUAAAAFRmFsc2UGFgAAAAIxMAYXAAAAATAGGAAAAAEwBhkAAAA7UGFnZWQ9VFJVRSZwX1JlbGVhc2VEYXRlPTIwMTkxMDA0JTIwMTIlM2EwMCUzYTI3JnBfSUQ9MjMzMTEGGgAAACZ7QTM2QjYzN0UtODE1RC00RDJFLUE1NTUtQzkwQkIzOTMyMjkyfQYbAAAAFDIwMjEtMDEtMDVUMDI6MjA6MzFaBhwAAABHP1BhZ2VkPVRSVUUmcF9SZWxlYXNlRGF0ZT0yMDIwMDMwNiAxMzowMDoyNyZwX0lEPTIzODQ1JlBhZ2VGaXJzdFJvdz0yMSYGHQAAAA9DdXJyZW50VXNlck5hbWULZGQFJmdfNmM0YTMzMGVfYjIxNF80N2I3X2JhMjdfZjEyZDY1ZTQ5ZjE3D2QWBGYPFgIfAGhkAgEPFgIfAGhkAiMPZBYCZg9kFgJmDzwrAAYAZAIHD2QWAgIHD2QWAgICD2QWAgIFD2QWAgIDDxYCHwBoFgJmD2QWBAICD2QWBgIBDxYCHwBoZAIDDxYCHwBoZAIFDxYCHwBoZAIDDw8WAh4JQWNjZXNzS2V5BQEvZGQYAgUQY3RsMDAkQ3VycmVudE5hdg8PZAUcTWFya2V0IE1vbml0b3JpbmcgJiBBbmFseXNpc2QFGWN0bDAwJFRvcE5hdmlnYXRpb25NZW51VjQPD2QFCEluZHVzdHJ5ZKstOKu0%2BP3a2tLeausAhhp9DimFvm4uhv3FLjWbVHyB&__VIEWSTATEGENERATOR=4E82104B&__EVENTVALIDATION=%2FwEdAAPRObgVjgfpHOSEjNusDUvtI9WgT0tlKuBuDzz85Ii%2FZMojbFppxYH1m8FlZhwcrxP80mkpicbwznBEQcAjcb8TBfEiruMQTI5v0Ia6JGDnFQ%3D%3D&ctl00%24g_70e4ef30_5add_4475_8351_8c8418406327%24ctl00%24txbKeyWord=");
                add("_wpcmWpid=&wpcmVal=&MSOWebPartPage_PostbackSource=&MSOTlPn_SelectedWpId=&MSOTlPn_View=0&MSOTlPn_ShowSettings=False&MSOGallery_SelectedLibrary=&MSOGallery_FilterString=&MSOTlPn_Button=none&__EVENTTARGET=ctl00%24ctl39%24g_bd8fd9d9_59af_4a24_b5ca_be6d9ec191f7&__EVENTARGUMENT=dvt_firstrow%3D%7B41%7D%3Bdvt_startposition%3D%7BPaged%3DTRUE%26p_ReleaseDate%3D20190506+12%3A00%3A03%26p_ID%3D22534%7D&__REQUESTDIGEST=0x9B6D89A5333F77DE50BE44E34D6332A79761691AF7824F6AB4F339DBFE54CE446CEAB3E93B90F0EAE60E7DDE69E4CB885D68771587E8D00C778BC0FD8FF72FF7%2C05+Jan+2021+07%3A21%3A24+-0000&MSOSPWebPartManager_DisplayModeName=Browse&MSOSPWebPartManager_ExitingDesignMode=false&MSOWebPartPage_Shared=&MSOLayout_LayoutChanges=&MSOLayout_InDesignMode=&_wpSelected=&_wzSelected=&MSOSPWebPartManager_OldDisplayModeName=Browse&MSOSPWebPartManager_StartWebPartEditingName=false&MSOSPWebPartManager_EndWebPartEditing=false&__VIEWSTATE=%2FwEPDwUBMA9kFgJmD2QWAgIBD2QWBAIBD2QWBgIXD2QWAgIDD2QWAmYPZBYCZg88KwAGAGQCGQ9kFgICAQ9kFgYFJmdfNDM4Y2QzN2FfNTU1Ml80YzZhX2JhN2ZfYmNkY2VhNGM4NDA2D2QWBGYPFgIeB1Zpc2libGVoZAIBDxYCHwBoZAUmZ19iZDhmZDlkOV81OWFmXzRhMjRfYjVjYV9iZTZkOWVjMTkxZjcPDxYSHgtEZXNjcmlwdGlvbmUeCURpcmVjdGlvbgsqKlN5c3RlbS5XZWIuVUkuV2ViQ29udHJvbHMuQ29udGVudERpcmVjdGlvbgAeBVRpdGxlBQpEYXRhVmlldyAxHgpDaHJvbWVUeXBlAgIeBF8hU0ICgIMIHgVXaWR0aBweEEZpbHRlck9wZXJhdGlvbnMyjQUAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAQBAAAAkwJTeXN0ZW0uQ29sbGVjdGlvbnMuR2VuZXJpYy5EaWN0aW9uYXJ5YDJbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV0sW01pY3Jvc29mdC5TaGFyZVBvaW50LldlYlBhcnRQYWdlcy5GaWx0ZXJPcGVyYXRpb24sIE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWNdXQMAAAAHVmVyc2lvbghDb21wYXJlcghIYXNoU2l6ZQADAAiSAVN5c3RlbS5Db2xsZWN0aW9ucy5HZW5lcmljLkdlbmVyaWNFcXVhbGl0eUNvbXBhcmVyYDFbW1N5c3RlbS5TdHJpbmcsIG1zY29ybGliLCBWZXJzaW9uPTQuMC4wLjAsIEN1bHR1cmU9bmV1dHJhbCwgUHVibGljS2V5VG9rZW49Yjc3YTVjNTYxOTM0ZTA4OV1dCAAAAAAJAgAAAAAAAAAEAgAAAJIBU3lzdGVtLkNvbGxlY3Rpb25zLkdlbmVyaWMuR2VuZXJpY0VxdWFsaXR5Q29tcGFyZXJgMVtbU3lzdGVtLlN0cmluZywgbXNjb3JsaWIsIFZlcnNpb249NC4wLjAuMCwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj1iNzdhNWM1NjE5MzRlMDg5XV0AAAAACx4GSGVpZ2h0HB4LUGFyYW1WYWx1ZXMy3wcAAQAAAP%2F%2F%2F%2F8BAAAAAAAAAAwCAAAAWE1pY3Jvc29mdC5TaGFyZVBvaW50LCBWZXJzaW9uPTE1LjAuMC4wLCBDdWx0dXJlPW5ldXRyYWwsIFB1YmxpY0tleVRva2VuPTcxZTliY2UxMTFlOTQyOWMFAQAAAD1NaWNyb3NvZnQuU2hhcmVQb2ludC5XZWJQYXJ0UGFnZXMuUGFyYW1ldGVyTmFtZVZhbHVlSGFzaHRhYmxlAQAAAAVfY29sbAMcU3lzdGVtLkNvbGxlY3Rpb25zLkhhc2h0YWJsZQIAAAAJAwAAAAQDAAAAHFN5c3RlbS5Db2xsZWN0aW9ucy5IYXNodGFibGUHAAAACkxvYWRGYWN0b3IHVmVyc2lvbghDb21wYXJlchBIYXNoQ29kZVByb3ZpZGVyCEhhc2hTaXplBEtleXMGVmFsdWVzAAADAwAFBQsIHFN5c3RlbS5Db2xsZWN0aW9ucy5JQ29tcGFyZXIkU3lzdGVtLkNvbGxlY3Rpb25zLklIYXNoQ29kZVByb3ZpZGVyCOxROD8yAAAACgolAAAACQQAAAAJBQAAABAEAAAADAAAAAYGAAAABlVzZXJJRAYHAAAABkxpbmtJRAYIAAAADU1hbnVhbFJlZnJlc2gGCQAAAAtNYXhpbXVtUm93cwYKAAAADGR2dF9maXJzdHJvdwYLAAAADG5leHRwYWdlZGF0YQYMAAAADVN0YXJ0Um93SW5kZXgGDQAAABBkdnRfbmV4dHBhZ2VkYXRhBg4AAAAGTGlzdElEBg8AAAAFVG9kYXkGEAAAAApGaWx0ZXJMaW5rBhEAAAARZHZ0X3N0YXJ0cG9zaXRpb24QBQAAAAwAAAAGEgAAAA9DdXJyZW50VXNlck5hbWUGEwAAAAM3MTEGFAAAAAVGYWxzZQYVAAAAAjEwBhYAAAACMzEGFwAAAAEwBhgAAAABMAYZAAAAO1BhZ2VkPVRSVUUmcF9SZWxlYXNlRGF0ZT0yMDE5MDUwNiUyMDEyJTNhMDAlM2EwMyZwX0lEPTIyNTM0BhoAAAAme0EzNkI2MzdFLTgxNUQtNEQyRS1BNTU1LUM5MEJCMzkzMjI5Mn0GGwAAABQyMDIxLTAxLTA1VDAyOjIxOjI0WgYcAAAARz9QYWdlZD1UUlVFJnBfUmVsZWFzZURhdGU9MjAxOTEwMDQgMTI6MDA6MjcmcF9JRD0yMzMxMSZQYWdlRmlyc3RSb3c9MzEmBh0AAAA1UGFnZWQ9VFJVRSZwX1JlbGVhc2VEYXRlPTIwMTkxMDA0IDEyOjAwOjI3JnBfSUQ9MjMzMTELZGQFJmdfNmM0YTMzMGVfYjIxNF80N2I3X2JhMjdfZjEyZDY1ZTQ5ZjE3D2QWBGYPFgIfAGhkAgEPFgIfAGhkAiMPZBYCZg9kFgJmDzwrAAYAZAIHD2QWAgIHD2QWAgICD2QWAgIFD2QWAgIDDxYCHwBoFgJmD2QWBAICD2QWBgIBDxYCHwBoZAIDDxYCHwBoZAIFDxYCHwBoZAIDDw8WAh4JQWNjZXNzS2V5BQEvZGQYAgUQY3RsMDAkQ3VycmVudE5hdg8PZAUcTWFya2V0IE1vbml0b3JpbmcgJiBBbmFseXNpc2QFGWN0bDAwJFRvcE5hdmlnYXRpb25NZW51VjQPD2QFCEluZHVzdHJ5ZP0g2Mile5JZ%2BEQ%2BgRbisHNIO3GwEyNeWDxKAPCTqNfc&__VIEWSTATEGENERATOR=4E82104B&__EVENTVALIDATION=%2FwEdAAM7kB6uEfGbjHIA0Pviwkt4I9WgT0tlKuBuDzz85Ii%2FZMojbFppxYH1m8FlZhwcrxPP%2BuDrb43HfJ7hIuTBhZenhim4UgoBoRzjxAQ4ixL41Q%3D%3D&ctl00%24g_70e4ef30_5add_4475_8351_8c8418406327%24ctl00%24txbKeyWord=");
            }
        };
        historyRecods.forEach(obj -> {
            String content = getContent(obj);
            Document document = Jsoup.parse(content);
            extractData(document);

        });

        records.values().forEach(link -> downloadExcel(link));
        disclosures.values().forEach(obj -> System.out.println(MessageFormat.format("{0}|{1}|{2}|{3}|{4}", obj.getIssuer_name(), obj.getTicker(), obj.getMarket(), obj.getNo_of_shorted_shares_position(), obj.getNet_change())));
    }

    private void extractData(Document document) {

        Elements elements = document.select("#WebPartWPQ3 > table:nth-child(1) > tbody>tr:not(:first-child)");

        for (Element element : elements) {
            String date = element.select("td:nth-child(1)").text();
            String link = "https://www.iiroc.ca/" + element.select("td:nth-child(2)>a").attr("href");
            records.put(date, link);
        }


    }

    private String getContent(String obj) {
        String response = "";
        try {
            URL url = new URL("https://www.iiroc.ca/industry/marketmonitoringanalysis/Pages/consolidated-short-position-report.aspx");
            HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
            postConnection.setConnectTimeout(10000);
            postConnection.setReadTimeout(10000);
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            postConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            postConnection.setRequestProperty("cache-control", "max-age=0");
            postConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
            postConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            postConnection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"");
            postConnection.setRequestProperty("sec-ch-ua-mobile", "?0");
            postConnection.setRequestProperty("sec-fetch-dest", "document");
            postConnection.setRequestProperty("sec-fetch-mode", "navigate");
            postConnection.setRequestProperty("sec-fetch-site", "same-origin");
            postConnection.setRequestProperty("sec-fetch-user", "?1");
            postConnection.setRequestProperty("upgrade-insecure-requests", "1");
//            postConnection.setRequestProperty("cookie", "SCOUTER=z79j613sf2lmhh; __utmz=88009422.1609154622.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmc=88009422; JSESSIONID=6595A39529A245165EB875C463E107CB.103tomcat2; __utma=88009422.1537191816.1609154622.1609310585.1609312508.6; __utmb=88009422.2.10.1609312508");
            postConnection.setRequestProperty("referrer", "https://www.iiroc.ca/industry/marketmonitoringanalysis/Pages/consolidated-short-position-report.aspx");
            postConnection.setRequestProperty("referrerPolicy", "strict-origin-when-cross-origin");
            postConnection.setRequestProperty("mode", "cors");

            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(obj.getBytes());
            os.flush();
            os.close();
            int statusCode = postConnection.getResponseCode();
            if (statusCode > 299)
                throw new RuntimeException(this.getClass().getName() + " -> : RESPONSE CODE :" + statusCode);

            try (InputStream inputStream = postConnection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder line = new StringBuilder();
                String line1;
                while ((line1 = reader.readLine()) != null) {
                    line.append(line1);
                }
                response = line.toString();
            }
        } catch (Exception ex) {
            System.out.println(ex.fillInStackTrace().toString());
        }
        return response;
    }

    private void readExcel(InputStream input) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);
        String sheetName = sheet.getSheetName();
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                if (row.getRowNum() == 1) break;

                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                rowData.add(cellValue);

            }
            if (rowData.size() == 5) {
                saveRow(rowData, sheetName);
            }
        }


    }

    private void saveRow(ArrayList<String> row, String sheet) {
        OtcCandaDisclosure disclosure = new OtcCandaDisclosure();
        disclosure.setIssuer_name(row.get(0));
        disclosure.setTicker(row.get(1));
        disclosure.setMarket(row.get(2));
        disclosure.setNo_of_shorted_shares_position(row.get(3));
        disclosure.setNet_change(row.get(4));
        String key = sheet + row.get(0) + row.get(1);
        disclosures.put(key, disclosure);

//        System.out.println("issuerName ->" + row.get(0));
//        System.out.println("ticker ->" + row.get(1));
//        System.out.println("Market ->" + row.get(2));
//        System.out.println(" no_of_shorted_shares_position ->" + row.get(3));
//        System.out.println("net_change ->" + row.get(4));
//        System.out.println("****");
    }

    private void downloadExcel(String url) {
        StringBuffer response = new StringBuffer();
        try {
            URL link = new URL(url);
            HttpsURLConnection getConnection = (HttpsURLConnection) link.openConnection();
            getConnection.setConnectTimeout(10000);
            getConnection.setReadTimeout(10000);
            getConnection.setRequestMethod("GET");
            getConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            try (InputStream inputStream = getConnection.getInputStream()) {
                System.out.println(url);
                readExcel(inputStream);
            }
        } catch (IOException | InvalidFormatException ex) {
            ex.getCause();
        }

    }
}