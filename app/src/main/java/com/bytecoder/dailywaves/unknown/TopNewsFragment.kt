package com.bytecoder.dailywaves.unknown

class TopNewsFragment : Fragment() {

    private lateinit var recyclerNews: RecyclerView
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_news, container, false)
        recyclerNews = view.findViewById(R.id.recyclerNews)
        shimmerLayout = view.findViewById(R.id.shimmerLayout)

        recyclerNews.layoutManager = LinearLayoutManager(requireContext())
        adapter = NewsAdapter(emptyList())
        recyclerNews.adapter = adapter

        loadNews() // Load API later
        return view
    }

    private fun loadNews() {
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE
        recyclerNews.visibility = View.GONE

        // TODO: Replace with API call (newsapi.org)
        Handler(Looper.getMainLooper()).postDelayed({
            val dummyNews = listOf(
                News("Breaking News Headline 1", "BBC", "https://picsum.photos/600/300"),
                News("Breaking News Headline 2", "CNN", "https://picsum.photos/600/301"),
                News("Breaking News Headline 3", "NDTV", "https://picsum.photos/600/302")
            )

            adapter.setNews(dummyNews)

            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            recyclerNews.visibility = View.VISIBLE
        }, 2000)
    }
}
