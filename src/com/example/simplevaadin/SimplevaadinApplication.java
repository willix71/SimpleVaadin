package com.example.simplevaadin;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SimplevaadinApplication extends Application {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		Window mainWindow = new Window("Simplevaadin Application");

		ObjectProperty<String> op = new ObjectProperty<String>("Hello");

		TextField editor = new TextField("Greetings");
		editor.setPropertyDataSource(op);
		editor.setImmediate(true);

		Label viewer = new Label();
		viewer.setPropertyDataSource(editor);

		mainWindow.addComponent(editor);
		mainWindow.addComponent(viewer);
		mainWindow.addComponent(new DatePopupInputPromptExample());
		setMainWindow(mainWindow);
	}

	class DatePopupInputPromptExample extends VerticalLayout implements
			ValueChangeListener {

		private DateField startDate;

		public DatePopupInputPromptExample() {
			setSpacing(true);
			startDate = new DateField("XZY") {
				@Override
				protected Date handleUnparsableDateString(String dateString)
						throws Property.ConversionException {
					Calendar now = Calendar.getInstance();
					// Try custom parsing
					String fields[] = dateString.split("/");
					if (fields.length >= 1) {
						try {							
							int day = Integer.parseInt(fields[0]);
							int month = fields.length>1? Integer.parseInt(fields[1]) - 1:now.get(Calendar.MONTH);
							int year = fields.length>2?Integer.parseInt(fields[0]):now.get(Calendar.YEAR);
							GregorianCalendar c = new GregorianCalendar(year, month, day);
							return c.getTime();
						} catch (NumberFormatException e) {
							throw new Property.ConversionException( "Not a number");
						}
					}
					// Bad date
					throw new Property.ConversionException(
							"Your date needs two slashes");
				}
			};
			//startDate.setInputPrompt("Start date");

			// Set the correct resolution
			startDate.setResolution(PopupDateField.RESOLUTION_MIN);
			startDate.setDateFormat("dd/MM/yyyy HH:mm");


			// Add valuechangelistener
			startDate.addListener(this);
			startDate.setImmediate(true);
			addComponent(startDate);
		}

		public void valueChange(ValueChangeEvent event) {
			// Get the new value and format it to the current locale
			DateFormat dateFormatter = DateFormat
					.getDateInstance(DateFormat.SHORT);
			Object value = event.getProperty().getValue();
			if (value == null || !(value instanceof Date)) {
				getWindow().showNotification("Invalid date entered");
			} else {
				String dateOut = dateFormatter.format(value);

				// Show notification
				getWindow().showNotification("Starting date: " + dateOut);
			}
		}
	}
}
