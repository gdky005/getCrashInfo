package log

/**
 * Created by WangQing on 15/12/7.
 */
class TakeFileContent {

    public String getContent(String filePaht) {

        StringBuffer stringBuffer = new StringBuffer()

        new File(filePaht).eachLine { line ->
            stringBuffer.append(line + "\r\n").replaceAll("#", "")
        }


        stringBuffer.toString()

    }
}
