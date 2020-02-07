package scrapers;

import java.util.ArrayList;
import java.util.List;

public class sUtil {

	public static List<String> parseTemplate(String template) {
		return parseTemplate(template, true);
	}

	public static List<String> parseTemplate(String template, boolean includeName) {
        List<String> ret = new ArrayList<>();

        int ind = 0;
        StringBuilder tmp = new StringBuilder();

        for (char c : template.toCharArray()) {
            ind += (c == '{') ? 1 : (c == '}') ? -1 : 0;

            if (ind == 2 && c == '|') {
                ret.add(tmp.toString());
                tmp = new StringBuilder();
            } else if ((c != '{' && ind == 2) || ind > 2) {
                tmp.append(c);
            }

        }

        ret.add(tmp.toString());

        System.out.println(ret);

        if (!includeName) ret.remove(0);
        return ret;
    }

	public static List<List<String>> parseTemplates(String input) {
        List<List<String>> r = new ArrayList<>();

        StringBuilder template = new StringBuilder();
        int ind = 0;

        for (char c : input.toCharArray()) {
            ind += (c == '{') ? 1 : (c == '}') ? -1 : 0;
            System.out.println(c + " " + ind);
            if (ind >= 1) template.append(c);
            else if (c == '}') {
                template.append('}');
                r.add(parseTemplate(template.toString()));
                template = new StringBuilder();
            }
        }

        return r;
    }
}
