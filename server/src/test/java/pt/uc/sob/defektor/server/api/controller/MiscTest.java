package pt.uc.sob.defektor.server.api.controller;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class MiscTest {

    @Test
    public void limit_array_list_size_java8 () {

//        List<Integer> contestents = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> contestents = Lists.newArrayList(1);

        List<Integer> contestWinners = contestents
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(5, contestWinners.size());
    }
}
