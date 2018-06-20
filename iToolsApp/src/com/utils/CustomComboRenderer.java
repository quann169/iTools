package com.utils;

import java.awt.Color;
import java.awt.Component;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager;

public class CustomComboRenderer extends DefaultListCellRenderer {
	public static final Color background = new Color(225, 240, 255);
	private static final Color defaultBackground = (Color) UIManager.get("List.background");
	private static final Color defaultForeground = (Color) UIManager.get("List.foreground");
	private Supplier<String> highlightTextSupplier;

	public CustomComboRenderer(Supplier<String> highlightTextSupplier) {
		this.highlightTextSupplier = highlightTextSupplier;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String text = (String) value;
		text = HtmlHighlighter.highlightText(text, highlightTextSupplier.get());
		this.setText(text);
		if (!isSelected) {
			this.setBackground(index % 2 == 0 ? background : defaultBackground);
		}
		this.setForeground(defaultForeground);
		return this;
	}
}

class HtmlHighlighter {
	private static final String HighLightTemplate = "<span style='background:yellow;'>$1</span>";

	public static String highlightText(String text, String textToHighlight) {
		if (textToHighlight.length() == 0) {
			return text;
		}

		try {
			text = text.replaceAll("(?i)(" + Pattern.quote(textToHighlight) + ")", HighLightTemplate);
		} catch (Exception e) {
			return text;
		}
		return "<html>" + text + "</html>";
	}
}
