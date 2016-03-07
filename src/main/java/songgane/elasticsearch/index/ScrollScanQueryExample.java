package songgane.elasticsearch.index;

public class ScrollScanQueryExample {
    public static void main(String[] args) {
//        String index = "mytest";
//        String type = "mytype";
//        QueryHelper qh = new QueryHelper();
//        qh.populateData(index, type);
//        Client client=qh.getClient();
//
//        QueryBuilder query = filteredQuery(boolQuery().must(rangeQuery("number1").gte(500)), termFilter("number2", 1));
//
//        SearchResponse response = client.prepareSearch(index).setTypes(type)
//                .setQuery(query).setScroll(TimeValue.timeValueMinutes(2))
//                .execute().actionGet();
//
//        // do something with searchResponse.getHits()
//        while(response.getHits().hits().length!=0){
//            // do something with searchResponse.getHits()
//            //your code here
//            //next scroll
//            response = client.prepareSearchScroll(response.getScrollId()).setScroll(TimeValue.timeValueMinutes(2)).execute().actionGet();
//        }
//
//        SearchResponse searchResponse = client.prepareSearch()
//                .setSearchType(SearchType.SCAN)
//                .setQuery(matchAllQuery())
//                .setSize(100)
//                .setScroll(TimeValue.timeValueMinutes(2))
//                .execute().actionGet();
//
//        while (true) {
//            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId()).setScroll(TimeValue.timeValueMinutes(2)).execute().actionGet();
//            // do something with searchResponse.getHits()
//
//            if (searchResponse.getHits().hits().length == 0) {
//                break;
//            }
//        }
//
//        qh.dropIndex(index);
    }
}
