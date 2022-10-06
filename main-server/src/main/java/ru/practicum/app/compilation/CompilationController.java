//package ru.practicum.app.compilation;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@RestController
//@Slf4j
//@Validated
//@RequestMapping
//public class CompilationController {
//
//    private final CompilationServiceImpl compilationService;
//
//    @Autowired
//    public CompilationController(CompilationServiceImpl  compilationService) {
//        this.compilationService = compilationService;
//    }
//
//    @GetMapping("/compilations")
//    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
//                                                @RequestParam(defaultValue = "0") int from,
//                                                @RequestParam(defaultValue = "10") int size) {
//        return compilationService.getCompilations(pinned, from, size);
//    }
//
//    @GetMapping("/compilations/{compId}")
//    public CompilationDto getCompilationById(@PathVariable Integer compId) {
//        return compilationService.getCompilationById(compId);
//    }
////
////    @PostMapping("/admin/compilations")
////    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
////        return compilationService.addCompilation(newCompilationDto);
////    }
////
////    @DeleteMapping("/admin/compilations/{compId}")
////    public void deleteCompilationById(@PathVariable Integer compId) {
////        compilationService.deleteCompilationById(compId);
////    }
////
////    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
////    public void addEventToCompilation(@PathVariable Integer compId,
////                                      @PathVariable Integer eventId) {
////        compilationService.addEventToCompilation(compId, eventId);
////    }
////
////    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
////    public void deleteEventFromCompilation(@PathVariable Integer compId,
////                                           @PathVariable Integer eventId) {
////        compilationService.deleteEventFromCompilation(compId, eventId);
////    }
////
////    @PatchMapping("/admin/compilations/{compId}/pin")
////    public void pinCompilation(@PathVariable Integer compId) {
////        compilationService.pinCompilation(compId);
////    }
////
////    @DeleteMapping("/admin/compilations/{compId}/pin")
////    public void unpinCompilation(@PathVariable Integer compId) {
////        compilationService.unpinCompilation(compId);
////    }
//
//}
