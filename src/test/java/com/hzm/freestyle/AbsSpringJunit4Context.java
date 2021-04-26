package com.hzm.freestyle;

import com.hzm.freestyle.service.BaseService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FreestyleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbsSpringJunit4Context extends BaseService {
}
