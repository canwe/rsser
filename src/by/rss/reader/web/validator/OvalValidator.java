package by.rss.reader.web.validator;

import org.springframework.validation.Errors;

import net.sf.oval.integration.spring.SpringValidator;

public class OvalValidator extends SpringValidator {
	private String profile;

	@Override
	public void validate(Object objectToValidate, Errors errors) {

		if (profile != null) {
			getValidator().disableAllProfiles();
			getValidator().enableProfile(profile);
		}

		super.validate(objectToValidate, errors);
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
}
