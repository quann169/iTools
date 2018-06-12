package vn.com.fwd.app.unsecure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@RequestMapping("login")
	public String showLogin(@PageableDefaults Pageable pageable, Model model) {
		return "public/login";
	}

}
